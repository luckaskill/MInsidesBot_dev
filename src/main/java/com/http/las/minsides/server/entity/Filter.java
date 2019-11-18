package com.http.las.minsides.server.entity;

import com.http.las.minsides.shared.entity.DaoEntity;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class Filter {
    Class<? extends DaoEntity> priority;
    Set<NoteType> selectedTypes;

    public boolean typesSelected() {
        return selectedTypes != null && !selectedTypes.isEmpty();
    }
}
