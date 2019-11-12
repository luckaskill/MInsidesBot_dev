package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.controller.exception.NoteValidatingFailed;
import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
//import com.http.las.minsides.server.CryptUtil;
import com.http.las.minsides.server.notes.dao.NoteTypeDao;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.tools.DaoUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {
    private NotesDao notesDao;
    private NoteTypeDao noteTypeDao;
//    private CryptUtil cryptUtil;
    @Override
    public void saveNote(Note note) {
        validateNote(note);
        Timestamp curTime = DaoUtil.getCurTime();
//        cryptUtil.encrypt(note);
        note.setDate(curTime);
        notesDao.persist(note);
    }

    private void validateNote(Note note) {
        if (note.getTitle() == null || note.getNote() == null) {
            throw new NoteValidatingFailed("Print at least title and main text ;)");
        }
    }

    @Override
    public List<Note> getAllNotes(Long chatId) {
        List<Note> allNotes = notesDao.getAllNotes(chatId);
//        cryptUtil.decrypt(allNotes);
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

}
