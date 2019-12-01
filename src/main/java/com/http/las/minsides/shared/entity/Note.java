package com.http.las.minsides.shared.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "paper_office")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Note extends DaoEntity {
    public static final String TO_SHORT_STRING_METHOD = "toShortString";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private long id;

    @Column(name = "note")
    private String text;
    private String title;
    private Timestamp date;
    @Column(name = "chat_id")
    private Long chatId;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "notes_types",
            joinColumns = {@JoinColumn(name = "note_id", referencedColumnName = "nid")},
            inverseJoinColumns = {@JoinColumn(name = "type_id", referencedColumnName = "nid")}
    )
    @EqualsAndHashCode.Exclude
    private List<NoteType> noteTypes = new ArrayList<>();

    public Note(String text, String title, Timestamp date, Long chatId) {
        this.text = text;
        this.title = title;
        this.date = date;
        this.chatId = chatId;
    }

    public Note setText(String note) {
        this.text = note;
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
        return "Note{" +
                "text='" + text + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String toFullString() {
        StringBuilder builder = new StringBuilder();
        String dateStr = date.toString();
        dateStr = dateStr.substring(0, dateStr.length() - 2);
        builder.append(title)
                .append("\n")
                .append("\n")
                .append(text)
                .append("\n")
                .append(dateStr);
        if (!CollectionUtils.isEmpty(noteTypes)) {
            builder.append("\nTypes:\n\n");
            for (NoteType type : noteTypes) {
                builder.append("   ").append(type.getTypeName()).append('\n');
            }
        }
        return builder.toString();
    }

    //use in reflection
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

    @Override
    public boolean equalsExcludeId(DaoEntity o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(text, note.text) &&
                Objects.equals(title, note.title) &&
                Objects.equals(date, note.date) &&
                Objects.equals(chatId, note.chatId);

    }

}
