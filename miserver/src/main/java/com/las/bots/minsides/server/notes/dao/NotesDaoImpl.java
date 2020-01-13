package com.las.bots.minsides.server.notes.dao;

import com.las.bots.minsides.server.base.BaseDaoImpl;
import com.las.bots.minsides.server.notes.config.HibSessionFactory;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
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
                "select distinct t FROM NoteType t where t.chatId=" + chatId, NoteType.class).list();
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
