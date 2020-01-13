package com.las.bots.minsides.server.entity;

import com.las.bots.minsides.shared.shared.entity.DaoEntity;
import com.las.bots.minsides.shared.shared.entity.NoteType;
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
