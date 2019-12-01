import com.http.las.minsides.shared.util.EntityUtil;
import com.http.las.minsides.shared.util.ReflectionUtil;
import entity.TestNote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ReflectionTest extends BaseTest {

    @Test
    void getterNameGenerateTest() {
        String getterGoToThe = ReflectionUtil.generateGetterName("goToThe");
        Assertions.assertEquals("getGoToThe", getterGoToThe);
    }

    @Test
    void readEntityFieldsValue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String text1 = "DEF2";
        String text2 = "def1";
        TestNote defaultNote1 = getDefaultNote();
        defaultNote1.setText(text1);
        TestNote defaultNote2 = getDefaultNote();
        defaultNote2.setText(text2);

        List<TestNote> testNotes = Arrays.asList(getDefaultNote(), defaultNote1, defaultNote2);
        List<Object> objects = EntityUtil.readEntityValues(testNotes, "text");

        Assertions.assertEquals(objects.get(0), text);
        Assertions.assertEquals(objects.get(1), text1);
        Assertions.assertEquals(objects.get(2), text2);


    }
}
