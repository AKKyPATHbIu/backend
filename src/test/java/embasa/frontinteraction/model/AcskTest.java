package embasa.frontinteraction.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class AcskTest {

    @Test
    public void testHashCode() {
        Acsk acsk = new Acsk();
        assertEquals(Objects.hash(acsk.getCode()), acsk.hashCode());
        acsk.setCode("code");
        assertEquals(Objects.hash(acsk.getCode()), acsk.hashCode());
    }

    @Test
    public void testEquals() {
        Acsk acsk = new Acsk();
        assertEquals(acsk, acsk);
        assertNotEquals(acsk, new Object());
        Acsk acsk1 = new Acsk();
        assertNotEquals(acsk, acsk1);
        acsk.setCode("code");
        assertNotEquals(acsk, acsk1);
        acsk1.setCode(acsk.getCode());
        assertEquals(acsk, acsk1);
    }
}