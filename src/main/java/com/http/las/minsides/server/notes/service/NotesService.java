package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface NotesService {
    void saveNote(Note note, byte[] bytes);

    List<Note> getAllNotes(Long chatId, byte[] bytes);

    List<NoteType> getUserNoteTypes(Long chatId);

    void saveNewNoteType(NoteType type);
}
