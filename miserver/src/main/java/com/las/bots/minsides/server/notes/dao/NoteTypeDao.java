package com.las.bots.minsides.server.notes.dao;

import com.las.bots.minsides.server.base.BaseDao;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface NoteTypeDao extends BaseDao<NoteType> {
}
