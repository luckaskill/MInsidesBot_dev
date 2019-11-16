import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
import com.http.las.minsides.server.notes.config.HibSessionFactory;
import com.http.las.minsides.server.notes.dao.NoteTypeDao;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

class TestClass extends ServerTest {
    @Autowired
    NotesService service;
    @Autowired
    NotesDao dao;
    @Autowired
    NoteTypeDao daoNT;

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
}
