package com.http.las.minsides.server.notes.dao;

import com.http.las.minsides.server.base.BaseDaoImpl;
import com.http.las.minsides.server.notes.config.HibSessionFactory;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.shared.entity.Person;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao {

    @Override
    public List<Person> getUserPeople(Long chatId) {
        @Cleanup
        Session session = HibSessionFactory.open();
        List<Person> people = session.createQuery(
                "SELECT DISTINCT p FROM Person as p " +
                        "WHERE p.chatId = " + chatId, Person.class).list();
        return people;
    }

}
