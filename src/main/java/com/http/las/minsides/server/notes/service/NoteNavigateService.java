package com.http.las.minsides.server.notes.service;

import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface NoteNavigateService {
    List<Note> getNotesByTypes(Set<NoteType> types);
}
