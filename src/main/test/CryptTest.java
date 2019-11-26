import com.http.las.minsides.controller.tools.Cryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CryptTest {
    @Test
    void crypt() {
        String value = "Crypt";
        byte[] bytes = {1, 2, 3, 4, 5, 9};
        String encrypt = Cryptor.encrypt(value, bytes);
        String decoded = Cryptor.decrypt(encrypt, bytes);
        Assertions.assertEquals(value, decoded);
    }
}
