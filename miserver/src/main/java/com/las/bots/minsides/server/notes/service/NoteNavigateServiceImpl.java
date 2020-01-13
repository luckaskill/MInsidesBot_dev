package com.las.bots.minsides.server.notes.service;

import com.las.bots.minsides.server.entity.Filter;
import com.las.bots.minsides.server.notes.dao.NotesDao;
import com.las.bots.minsides.server.notes.tools.Librarian;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NoteNavigateServiceImpl implements NoteNavigateService {
    private NotesDao dao;

    @Override
    public List<Note> getNotesByTypes(Set<NoteType> types) {
        List<Note> notes = dao.findAnyMatch(types);

        Filter filter = new Filter(NoteType.class, types);
        Librarian.sort(notes, filter);
        return notes;
    }
}
