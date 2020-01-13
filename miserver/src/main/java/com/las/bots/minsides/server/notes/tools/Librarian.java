package com.las.bots.minsides.server.notes.tools;

import com.las.bots.minsides.server.entity.Filter;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;

import java.util.Comparator;
import java.util.List;

public class Librarian {
    public static void sort(List<Note> notes, Filter filter) {
        Comparator<Note> comparator = (o1, o2) -> {
            if (filter.typesSelected()) {
                List<NoteType> noteTypes1 = o1.getNoteTypes();
                List<NoteType> noteTypes2 = o2.getNoteTypes();
                int size1 = noteTypes1.size();
                int size2 = noteTypes2.size();

                int compare = Integer.compare(size1, size2);
                return compare;
            }
            return 0;
        };
        notes.sort(comparator);
    }


}
