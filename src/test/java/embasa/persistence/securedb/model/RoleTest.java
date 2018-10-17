package embasa.persistence.securedb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class RoleTest {

    @Test
    public void testHashCode() {
        Role r = new Role();
        assertEquals(Objects.hash(r.getName(), r.getDescription()), r.hashCode());
        r.setName("name");
        assertEquals(Objects.hash(r.getName(), r.getDescription()), r.hashCode());
        r.setDescription("descr");
        assertEquals(Objects.hash(r.getName(), r.getDescription()), r.hashCode());
        r.setId(4444L);
        assertEquals(r.getId().hashCode(), r.hashCode());
    }

    @Test
    public void testEquals() {
        Role r1 = new Role();
        assertEquals(r1, r1);
        Role r2 = new Role();
        assertNotEquals(r1, r2);
        r1.setName("name");
        assertNotEquals(r1, r2);
        r2.setName(r1.getName());
        assertEquals(r1, r2);
        r1.setDescription("descr");
        assertNotEquals(r1, r2);
        r2.setDescription(r1.getDescription());
        assertEquals(r1, r2);
        r1.setId(100000000L);
        assertNotEquals(r1, r2);
        r2.setId(r1.getId());
        assertEquals(r1, r2);
    }
}