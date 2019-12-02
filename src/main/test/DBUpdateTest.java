import com.http.las.minsides.server.notes.config.HibSessionFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DBUpdateTest {
    @Test
    void findPatchesAfterZero() throws IOException {
        @Cleanup final Session session = HibSessionFactory.open();
        NativeQuery sqlQuery = session.createSQLQuery("ALTER TABLE notes_types ADD COLUMN chat_id INTEGER;");
        sqlQuery.executeUpdate();

//        URL patches = getClass().getClassLoader().getResource("patches");
//        String path = patches.getPath();
//        File[] files = new File(path).listFiles();
//        final StringBuilder query = new StringBuilder();
//        for (File file : files) {
//            Files.lines(Paths.get(file.getCanonicalPath()))
//                    .forEach(query::append);
//        }
//        System.out.println(query.toString());
    }
}
