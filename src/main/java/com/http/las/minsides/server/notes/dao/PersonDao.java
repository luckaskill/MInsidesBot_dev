package com.http.las.minsides.server.notes.dao;

import com.http.las.minsides.server.base.BaseDao;
import com.http.las.minsides.shared.entity.Person;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PersonDao extends BaseDao<Person> {
    List<Person> getUserPeople(Long chatId);
}
