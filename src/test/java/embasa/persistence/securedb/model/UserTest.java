package embasa.persistence.securedb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testHashCode() {
        User u = new User();
        assertEquals(Objects.hash(u.getUsername()), u.hashCode());
        u.setUsername("username");
        assertEquals(Objects.hash(u.getUsername()), u.hashCode());
        u.setId(1L);
        assertEquals(u.getId().hashCode(), u.hashCode());
    }

    @Test
    public void testEquals() {
        User u1 = new User();
        assertEquals(u1, u1);
        User u2 = new User();
        assertNotEquals(u1, u2);
        u1.setUsername("user1");
        assertNotEquals(u1, u2);
        u2.setUsername(u1.getUsername());
        assertEquals(u1, u2);
        u1.setId(1L);
        assertNotEquals(u1, u2);
        u2.setId(u1.getId());
        assertEquals(u1, u2);
    }
}