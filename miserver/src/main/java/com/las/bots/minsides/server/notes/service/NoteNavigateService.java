package com.las.bots.minsides.server.notes.service;

import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface NoteNavigateService {
    List<Note> getNotesByTypes(Set<NoteType> types);
}
