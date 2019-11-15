import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
import com.http.las.minsides.server.notes.config.HibSessionFactory;
import com.http.las.minsides.server.notes.dao.NoteTypeDao;
import com.http.las.minsides.server.notes.dao.NoteTypeDaoImpl;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.dao.NotesDaoImpl;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.server.notes.service.NotesServiceImpl;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {NotesServiceImpl.class, NotesDaoImpl.class, NoteTypeDaoImpl.class})
@ExtendWith(SpringExtension.class)
public class TestClass {
    @Autowired
    NotesService service;
    @Autowired
    NotesDao dao;

    @Test
    void saveNoteWithTypesThenDelete() {
        Note note = new Note("note", "title", new Timestamp(System.currentTimeMillis()), 12L);
        NoteType type1 = new NoteType("new type1!!!!!!!!!!");
        NoteType type2 = new NoteType("new type2!!!!!!!!!!");
        List<NoteType> noteTypes = note.getNoteTypes();
        noteTypes.add(type1);
        noteTypes.add(type2);

        @Cleanup
        Session session = HibSessionFactory.open();
        String query = "FROM NoteType";

        List<NoteType> list1 = session.createQuery(query, NoteType.class).list();
        dao.persist(note);

        List<NoteType> list2 = session.createQuery(query, NoteType.class).list();
        Assertions.assertTrue(list2.size() > list1.size());
        dao.remove(note);

        List<NoteType> list3 = session.createQuery(query, NoteType.class).list();
        Assertions.assertEquals(list3.size(), list1.size());

    }
}
