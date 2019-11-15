package com.http.las.minsides.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "paper_office")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Note implements DaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private long id;

    private String note;
    private String title;
    private Timestamp date;
    @Column(name = "chat_id")
    private Long chatId;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "notes_types",
            joinColumns = {@JoinColumn(name = "note_id", referencedColumnName = "nid")},
            inverseJoinColumns = {@JoinColumn(name = "type_id", referencedColumnName = "nid")}
    )
    @EqualsAndHashCode.Exclude
    private List<NoteType> noteTypes = new ArrayList<>();

    public Note(String note, String title, Timestamp date, Long chatId) {
        this.note = note;
        this.title = title;
        this.date = date;
        this.chatId = chatId;
    }

    public Note setNote(String note) {
        this.note = note;
        return this;
    }

    public Note setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public Note setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public Note setNoteTypes(List<NoteType> noteTypes) {
        this.noteTypes = noteTypes;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String dateStr = date.toString();
        dateStr = dateStr.substring(0, dateStr.length() - 2);
        builder.append(title)
                .append("\n")
                .append("\n")
                .append(note)
                .append("\n")
                .append(dateStr);
        if (!CollectionUtils.isEmpty(noteTypes)) {
            builder.append("Types:\n\n");
            for (NoteType type : noteTypes) {
                builder.append("   ").append(type.getTypeName()).append('\n');
            }
        }
        return builder.toString();
    }

    public String toShortString() {
        StringBuilder builder = new StringBuilder();
        String dateStr = date.toString();
        dateStr = dateStr.substring(0, dateStr.length() - 2);
        return builder.append("\n")
                .append(title)
                .append("\n")
                .append(dateStr)
                .append("\n").toString();
    }
}
