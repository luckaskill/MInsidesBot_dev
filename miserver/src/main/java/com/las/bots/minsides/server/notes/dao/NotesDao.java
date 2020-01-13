package com.las.bots.minsides.server.notes.dao;

import com.las.bots.minsides.server.base.BaseDao;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Transactional
public interface NotesDao extends BaseDao<Note> {
    List<Note> getAllNotes(Long chatId);

    List<NoteType> getUserNoteTypes(Long chatId);

    List<Note> findAnyMatch(Set<NoteType> types);
}
