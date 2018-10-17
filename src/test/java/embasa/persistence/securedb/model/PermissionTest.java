package embasa.persistence.securedb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class PermissionTest {

    @Test
    public void testHashCode() {
        Permission p = new Permission();
        assertEquals(Objects.hash(p.getNameCode(), p.getDescCode()), p.hashCode());
        p.setNameCode("name_code");
        assertEquals(Objects.hash(p.getNameCode(), p.getDescCode()), p.hashCode());
        p.setDescCode("descr_code");
        assertEquals(Objects.hash(p.getNameCode(), p.getDescCode()), p.hashCode());
        p.setId(777L);
        assertEquals(p.getId().hashCode(), p.hashCode());
    }

    @Test
    public void testEquals() {
        Permission p1 = new Permission();
        assertEquals(p1, p1);
        Permission p2 = new Permission();
        assertNotEquals(p1, p2);

        p1.setNameCode("name_code");
        assertNotEquals(p1, p2);
        p2.setNameCode(p1.getNameCode());
        assertEquals(p1, p2);
        p1.setDescCode("descr_code");
        assertNotEquals(p1, p2);
        p2.setDescCode(p1.getDescCode());
        assertEquals(p1, p2);

        p1.setId(321654L);
        assertNotEquals(p1, p2);
        p2.setId(p1.getId());
        assertEquals(p1, p2);
    }
}