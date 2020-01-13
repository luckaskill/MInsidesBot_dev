package com.las.bots.minsides.server.notes.service;


import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import com.las.bots.minsides.shared.shared.entity.Person;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NotesService {
    void saveNote(Note note, byte[] bytes);

    List<Note> getAllNotes(Long chatId, byte[] bytes);

    List<NoteType> getUserNoteTypes(Long chatId);

    void saveNewNoteType(NoteType type);

    List<Person> getUserPeople(Long chatId);

    void saveNewPerson(Person person);

}
