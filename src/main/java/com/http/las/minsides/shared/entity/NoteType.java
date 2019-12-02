package com.http.las.minsides.shared.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "types")
@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class NoteType extends DaoEntity {
    public static final String TYPE_NAME_FIELDNAME = "typeName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private long id;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "chat_id")
    private Integer chatId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "noteTypes")
    @EqualsAndHashCode.Exclude
    private List<Note> notes = new ArrayList<>();

    public NoteType(String typeName) {
        this.typeName = typeName;
    }

    public NoteType setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public NoteType setNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public boolean equalsExcludeId(DaoEntity o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteType noteType = (NoteType) o;
        return typeName.equals(noteType.typeName) &&
                notes.equals(noteType.notes);
    }

}
