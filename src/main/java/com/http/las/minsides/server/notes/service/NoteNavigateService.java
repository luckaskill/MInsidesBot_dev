package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;

import java.util.List;
import java.util.Set;

public interface NoteNavigateService {
    List<Note> getNotesByTypes(Set<NoteType> types);
}
