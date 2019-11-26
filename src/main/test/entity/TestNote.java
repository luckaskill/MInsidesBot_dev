package entity;

import com.http.las.minsides.shared.entity.DaoEntity;
import com.http.las.minsides.shared.entity.IgnoreDump;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "paper_office")
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
public class TestNote extends DaoEntity {
    @Id
    private long id;
    private String text;
    private String title;
    private Timestamp date;
    private Long chatId;
    @IgnoreDump
    private List<NoteType> noteTypes = new ArrayList<>();

    public TestNote(String text, String title, Timestamp date, Long chatId) {
        this.text = text;
        this.title = title;
        this.date = date;
        this.chatId = chatId;
    }

    @Override
    public boolean equalsExcludeId(DaoEntity o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestNote note = (TestNote) o;
        return Objects.equals(text, note.text) &&
                Objects.equals(title, note.title) &&
                Objects.equals(date, note.date) &&
                Objects.equals(chatId, note.chatId);
    }

    public TestNote setText(String note) {
        this.text = note;
        return this;
    }

    public TestNote setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public TestNote setTitle(String title) {
        this.title = title;
        return this;
    }

    public TestNote setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public TestNote setNoteTypes(List<NoteType> noteTypes) {
        this.noteTypes = noteTypes;
        return this;
    }

    public TestNote setId(long id) {
        this.id = id;
        return this;
    }

    public DaoEntity getDump() {
        return dump;
    }

}
