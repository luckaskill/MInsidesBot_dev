package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.controller.exception.NoteValidatingFailed;
import com.http.las.minsides.server.notes.dao.PersonDao;
import com.http.las.minsides.server.tools.Cryptor;
import com.http.las.minsides.server.notes.dao.NoteTypeDao;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.tools.DaoUtil;
import com.http.las.minsides.shared.Consts;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.shared.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {
    private NotesDao notesDao;
    private NoteTypeDao noteTypeDao;
    private PersonDao personDao;

    @Override
    public void saveNote(Note note, byte[] bytes) {
        validateNote(note);
        cryptNote(note, bytes);
        Timestamp curTime = DaoUtil.getCurTime();
        note.setDate(curTime);
        notesDao.persist(note);
    }

    private void cryptNote(Note note, byte[] key) {
        String text = note.getText();
        String encryptText = Cryptor.encrypt(text, key);
        note.setText(encryptText);
    }

    private static void decryptNote(List<Note> notes, byte[] key) {
        for (Note note : notes) {
            note.createDump();
            try {
                String text = note.getText();
                String decryptText = Cryptor.decrypt(text, key);
                note.setText(decryptText);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Unable to decode note: " + note + " - " + e.getMessage());
                note.backup();
                note.setText(Consts.CRYPTED);
            }
        }
    }

    private void validateNote(Note note) {
        boolean emptyTitle = note.getTitle() == null;
        boolean emptyText = note.getText() == null;
        if (emptyTitle || emptyText) {
            throw new NoteValidatingFailed("Print at least title and main text ;)");
        }
    }

    @Override
    public List<Note> getAllNotes(Long chatId, byte[] bytes) {
        List<Note> allNotes = notesDao.getAllNotes(chatId);
        decryptNote(allNotes, bytes);
        return allNotes;
    }

    @Override
    public List<NoteType> getUserNoteTypes(Long chatId) {
        List<NoteType> notes = notesDao.getUserNoteTypes(chatId);
        return notes;
    }

    @Override
    public void saveNewNoteType(NoteType type) {
        noteTypeDao.save(type);
    }

    @Override
    public List<Person> getUserPeople(Long chatId) {
        List<Person> userPeople = personDao.getUserPeople(chatId);
        return userPeople;
    }

    @Override
    public void saveNewPerson(Person person) {
        personDao.save(person);
    }

}
