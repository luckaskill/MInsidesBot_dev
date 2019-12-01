import com.http.las.minsides.shared.entity.NoteType;
import entity.TestNote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DaoEntityTest extends BaseTest {

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

}
