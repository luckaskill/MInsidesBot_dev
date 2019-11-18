package com.http.las.minsides.server.notes.dao;

import com.http.las.minsides.server.base.BaseDaoImpl;
import com.http.las.minsides.server.notes.config.HibSessionFactory;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
class NotesDaoImpl extends BaseDaoImpl<Note> implements NotesDao {

    @Override
    public List<Note> getAllNotes(Long chatId) {
        @Cleanup
        Session session = HibSessionFactory.open();
        List<Note> result = session.createQuery("FROM Note WHERE chatId = " + chatId, Note.class).list();
        return result;
    }

    @Override
    public List<NoteType> getUserNoteTypes(Long chatId) {
        @Cleanup
        Session session = HibSessionFactory.open();
        List<NoteType> noteTypes = session.createQuery(
                "SELECT nt FROM NoteType as nt " +
                        "INNER JOIN nt.notes as notes " +
                        "ON notes.chatId = " + chatId, NoteType.class).list();
        return noteTypes;
    }

    @Override
    public List<Note> findAnyMatch(Set<NoteType> types) {
        @Cleanup
        Session session = HibSessionFactory.open();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT n FROM Note as n LEFT JOIN no.noteTypes AS tp ON n.nid = tp.nid");
        if (!types.isEmpty()) {
            builder.append("WHERE tp.nid in(");
            for (NoteType type : types) {
                builder.append(type.getId()).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        List<Note> result = session.createQuery(builder.toString(), Note.class).list();
        return result;
    }


}
