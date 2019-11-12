package java.com.http.las.minsides.server.notes.service;

import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
import com.http.las.minsides.server.notes.config.HibSessionFactory;
import com.http.las.minsides.server.notes.dao.NotesDao;
import com.http.las.minsides.server.notes.dao.NotesDaoImpl;
import com.http.las.minsides.server.notes.service.NotesService;
import com.http.las.minsides.server.notes.service.NotesServiceImpl;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {NotesServiceImpl.class, NotesDaoImpl.class})
class NotesServiceImplTest {

    @Autowired
    NotesService service;
    @Autowired
    NotesDao dao;

    @Test
    void saveNoteWithNoteTypes() {
        Note note = new Note("note", "title", new Timestamp(System.currentTimeMillis()), 12L);
        NoteType type1 = new NoteType("new type1!!!!!!!!!!");
        NoteType type2 = new NoteType("new type2!!!!!!!!!!");
        List<NoteType> noteTypes = note.getNoteTypes();
        noteTypes.add(type1);
        noteTypes.add(type2);

        @Cleanup
        Session session = HibSessionFactory.open();
        List list = session.createSQLQuery("SELECT * FROM notes_types").list();
        Assertions.assertNotNull(list);
    }

    @Test
    void saveNote() {
        Note note = new Note("note", "title", new Timestamp(System.currentTimeMillis()), 12L);
        NoteType type1 = new NoteType("new type1!!!!!!!!!!");
        NoteType type2 = new NoteType("new type2!!!!!!!!!!");
        List<NoteType> noteTypes = note.getNoteTypes();
        noteTypes.add(type1);
        noteTypes.add(type2);

        @Cleanup
        Session session = HibSessionFactory.open();
        List<Note> list = session.createQuery("from Note LEFT JOIN NoteType", Note.class).list();
        System.out.println(list);
    }


}