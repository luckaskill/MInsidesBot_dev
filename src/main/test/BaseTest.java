import com.las.bots.minsides.shared.shared.entity.NoteType;
import entity.TestNote;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(locations = "/application_context_test.xml")
@SpringBootTest
public class BaseTest {
    protected static String text = "TEXT";
    protected static String title = "TITLE";
    protected static String type1 = "TYPE1";
    protected static String type2 = "TYPE2";
    protected static List<NoteType> types = Arrays.asList(new NoteType(type1), new NoteType(type2));
    protected static Timestamp date = new Timestamp(System.currentTimeMillis());
    protected static long id = 1;

    public static TestNote getDefaultNote() {
        TestNote note = new TestNote(text, title, date, id).setNoteTypes(types).setId(id);
        return note;
    }
}
