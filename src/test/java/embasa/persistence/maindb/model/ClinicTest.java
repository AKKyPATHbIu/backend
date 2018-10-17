package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class ClinicTest {

    private final Long id = 999999L;
    private final String name = "Клиника доктора Айболита";
    private final String descr = "Лучшая клиника в мире!";
    private final Long status = 1000000L;

    @Test
    public void testHashCode() {
        Clinic c = new Clinic();
        assertEquals(Objects.hash(c.getName(), c.getDescription(), c.getStatus()), c.hashCode());
        c.setName(name);
        assertEquals(Objects.hash(c.getName(), c.getDescription(), c.getStatus()), c.hashCode());
        c.setDescription(descr);
        assertEquals(Objects.hash(c.getName(), c.getDescription(), c.getStatus()), c.hashCode());
        c.setStatus(status);
        assertEquals(Objects.hash(c.getName(), c.getDescription(), c.getStatus()), c.hashCode());
        c.setId(id);
        assertEquals(id.hashCode(), c.hashCode());
    }

    @Test
    public void testEquals() {
        Clinic c = new Clinic();
        assertEquals(c, c);
        assertNotEquals(c, new Object());

        Clinic c1 = new Clinic();
        assertNotEquals(c, c1);

        c.setName(name);
        assertNotEquals(c, c1);
        c1.setName(name);
        assertEquals(c, c1);

        c.setDescription(descr);
        assertNotEquals(c, c1);
        c1.setDescription(descr);
        assertEquals(c, c1);
        c.setStatus(status);
        assertNotEquals(c, c1);
        c1.setStatus(status);
        assertEquals(c, c1);
        c.setId(id);
        assertNotEquals(c, c1);
        c1.setId(id);
        assertEquals(c, c1);
    }

    @Test
    public void test() {
        Clinic c = new Clinic();
        c.setId(id);
        c.setName(name);
        c.setDescription(descr);
        c.setStatus(status);
        assertEquals(id, c.getId());
        assertEquals(descr, c.getDescription());
        assertEquals(name, c.getName());
        assertEquals(status, c.getStatus());
    }
}