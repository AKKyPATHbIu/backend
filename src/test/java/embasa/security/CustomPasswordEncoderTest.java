package embasa.security;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomPasswordEncoderTest {

    CustomPasswordEncoder encoder = new CustomPasswordEncoder();

    @Test
    public void test() {
        String password = "S3CRET";
        String badPassword = "S3CRET_1";

        String hash = encoder.encode(password);
        assertTrue(encoder.matches(password, hash));
        assertFalse(encoder.matches(badPassword, hash));
        assertFalse(encoder.matches(hash, hash));

        hash = encoder.encode(badPassword);
        assertTrue(encoder.matches(badPassword, hash));
        assertFalse(encoder.matches(password, hash));
        assertFalse(encoder.matches(hash, hash));
    }
}