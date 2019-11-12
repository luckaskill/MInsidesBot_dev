package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;

import java.util.List;

public interface NotesService {
    void saveNote(Note note);

    List<Note> getAllNotes(Long chatId);

    List<NoteType> getUserNoteTypes(Long chatId);

    void saveNewNoteType(NoteType type);
}
