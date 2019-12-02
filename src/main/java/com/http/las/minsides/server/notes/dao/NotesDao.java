package com.http.las.minsides.server.notes.dao;

import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.base.BaseDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface NotesDao extends BaseDao<Note> {
    List<Note> getAllNotes(Long chatId);

    List<NoteType> getUserNoteTypes(Long chatId);

    List<Note> findAnyMatch(Set<NoteType> types);
}
