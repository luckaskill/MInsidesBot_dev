import com.http.las.minsides.shared.entity.NoteType;
import entity.TestNote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaoEntityTest {
    private static String text = "TEXT";
    private static String title = "TITLE";
    private static String type1 = "TYPE1";
    private static String type2 = "TYPE2";
    private static List<NoteType> types = Arrays.asList(new NoteType(type1), new NoteType(type2));
    private static Timestamp date = new Timestamp(System.currentTimeMillis());
    private static long id = 1;

    @Test
    void fullBackupTest() {
        String newData = "new";
        TestNote defaultNote1 = getDefaultNote();

        defaultNote1.createDump();
        TestNote dump = (TestNote) defaultNote1.getDump();
        Assertions.assertNotEquals(defaultNote1.getId(), dump.getId());
        Assertions.assertNotEquals(defaultNote1.getNoteTypes(), dump.getNoteTypes());

        defaultNote1.setTitle(newData);
        defaultNote1.setText(newData);
        defaultNote1.setChatId(2L);
        defaultNote1.setDate(new Timestamp(1));
        ArrayList<NoteType> noteTypes = new ArrayList<>(types);
        noteTypes.remove(0);
        defaultNote1.setNoteTypes(noteTypes);

        TestNote defaultNote2 = getDefaultNote();
        Assertions.assertNotEquals(defaultNote1, defaultNote2);
        Assertions.assertTrue(dump.equalsExcludeId(defaultNote2));

        defaultNote1.backup();
        Assertions.assertTrue(defaultNote1.equalsExcludeId(defaultNote2));
        Assertions.assertNotEquals(defaultNote1.getNoteTypes(), defaultNote2.getNoteTypes());
    }


    public static TestNote getDefaultNote() {
        TestNote note = new TestNote(text, title, date, id).setNoteTypes(types).setId(id);
        return note;
    }
}
