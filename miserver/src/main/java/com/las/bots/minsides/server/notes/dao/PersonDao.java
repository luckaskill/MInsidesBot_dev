package com.las.bots.minsides.server.notes.dao;


import com.las.bots.minsides.server.base.BaseDao;
import com.las.bots.minsides.shared.shared.entity.Person;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PersonDao extends BaseDao<Person> {
    List<Person> getUserPeople(Long chatId);
}
