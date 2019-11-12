package com.http.las.minsides.entity;

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
public class NoteType implements DaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private long id;

    @Column(name = "type_name")
    private String typeName;

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
}
