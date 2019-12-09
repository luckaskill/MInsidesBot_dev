import com.http.las.minsides.server.notes.dao.PersonDao;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.notes.dao.NoteTypeDao;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.shared.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

class TestClass extends BaseTest {
    @Autowired
    NotesService service;
    @Autowired
    NotesDao dao;
    @Autowired
    NoteTypeDao daoNT;
    @Autowired
    PersonDao personDao;

    @Test
    void saveNoteWithTypesThenDelete() {
        Note note = new Note("NOTE!!!", "TITLE!!!!", new Timestamp(System.currentTimeMillis()), 12L);
        NoteType type1 = new NoteType("type1");
        NoteType type2 = new NoteType("type2");
        List<NoteType> noteTypes = note.getNoteTypes();
        noteTypes.add(type1);
        noteTypes.add(type2);

        NoteType type3 = daoNT.select(41);
        note.getNoteTypes().add(type3);
        List<NoteType> list1 = daoNT.selectAll();
        dao.persist(note);

        List<NoteType> list2 = daoNT.selectAll();
        Assertions.assertTrue(list2.size() > list1.size());
        dao.remove(note);

        List<NoteType> list3 = daoNT.selectAll();
        Assertions.assertNotEquals(list3.size(), list1.size());
    }

    @Test
    void getAllNotes() {
        List<Note> allNotes = service.getAllNotes(96114053L, null);
    }

    @Test
    void savePerson() {
        Person person = new Person("Maxim soset", 12L);
        personDao.save(person);
    }

    @Test
    void getPeopleByUser() {
        List<Person> userPeople = personDao.getUserPeople(12L);
    }

    @Test
    void addPersonToNote() {
//        Note note = new Note("NOTE!!!", "TITLE!!!!", new Timestamp(System.currentTimeMillis()), 12L);
//        dao.save(note);

        List<Person> people = personDao.selectAll();
        List<Note> notes = dao.selectAll();

        notes.get(0).getNotePeople().add(people.get(0));

        dao.persist(notes.get(0));

        List<Person> people1 = personDao.selectAll();
        List<Note> notes1 = dao.selectAll();
    }

    @Test
    void getAllPerson() {
        List<Person> people = personDao.selectAll();
        System.out.println();
    }

}
