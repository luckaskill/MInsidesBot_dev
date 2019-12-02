package com.http.las.minsides.server.notes.dao;

import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.base.BaseDao;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface NoteTypeDao extends BaseDao<NoteType> {
}
