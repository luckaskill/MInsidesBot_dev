package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.entity.Filter;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.tools.Librarian;
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
