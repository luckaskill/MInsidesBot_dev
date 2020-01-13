package com.las.bots.minsides.server.notes.service;

import com.las.bots.minsides.server.notes.dao.NoteTypeDao;
import com.las.bots.minsides.server.notes.dao.NotesDao;
import com.las.bots.minsides.server.notes.dao.PersonDao;
import com.las.bots.minsides.server.notes.tools.DaoUtil;
import com.las.bots.minsides.server.tools.Cryptor;
import com.las.bots.minsides.shared.shared.Consts;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import com.las.bots.minsides.shared.shared.entity.Person;
import com.las.bots.minsides.shared.shared.exception.NoteValidatingFailedExceptoion;
import com.las.bots.minsides.shared.shared.util.EntityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {
    private NotesDao notesDao;
    private NoteTypeDao noteTypeDao;
    private PersonDao personDao;

    public NotesServiceImpl(NotesDao notesDao, NoteTypeDao noteTypeDao, PersonDao personDao) {
        this.notesDao = notesDao;
        this.noteTypeDao = noteTypeDao;
        this.personDao = personDao;
        EntityUtil.I = 20000;
        System.out.println("Server");
        System.out.println(EntityUtil.I);
    }

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
            throw new NoteValidatingFailedExceptoion("Print at least title and main text ;)");
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
