package com.http.las.minsides.shared.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "people")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Person extends DaoEntity {
    public static final String PERSON_NAME_FIELDNAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private long id;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "p_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "notePeople")
    @EqualsAndHashCode.Exclude
    private List<Note> notes = new ArrayList<>();

    public Person(String name, long chatId) {
        this.chatId = chatId;
        this.name = name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setChatId(Integer chatId) {
        this.chatId = chatId;
        return this;
    }

    @Override
    public boolean equalsExcludeId(DaoEntity o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(notes, person.notes);
    }
}
