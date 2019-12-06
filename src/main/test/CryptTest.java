import com.http.las.minsides.server.tools.Cryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CryptTest {
    @Test
    void crypt() {
        String value = "Crypt";
        byte[] bytes = {1, 2, 3, 4, 5, 9};
        String encrypt1 = Cryptor.encrypt(value, bytes);
        String encrypt2 = Cryptor.encrypt(value, bytes);
        Assertions.assertNotEquals(encrypt1, encrypt2);

        String decoded1 = Cryptor.decrypt(encrypt1, bytes);
        String decoded2 = Cryptor.decrypt(encrypt2, bytes);
        Assertions.assertEquals(value, decoded1);
        Assertions.assertEquals(value, decoded2);
    }
}
