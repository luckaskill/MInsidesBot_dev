package com.http.las.minsides.controller.storage;

import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSessionInfo {
    public static final Map<Long, Note> NOTES_IN_CREATION = new HashMap<>();
    public static final Map<Long, List<Note>> USER_NOTES = new HashMap<>();
    public static final Map<Long, List<NoteType>> USER_NOTE_TYPES = new HashMap<>();
    public static final Map<Long, NoteType> TYPES_TO_SAVE = new HashMap<>();
}
