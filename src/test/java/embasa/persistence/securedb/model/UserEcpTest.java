package embasa.persistence.securedb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class UserEcpTest {

    @Test
    public void testHashCode() {
        User u = new User();
        u.setId(999L);
        UserEcp ecp = new UserEcp();
        ecp.setUser(u);
        assertEquals(Objects.hash(u, ecp.getValue()), ecp.hashCode());
        ecp.setId(20L);
        assertEquals(ecp.getId().hashCode(), ecp.hashCode());
    }

    @Test
    public void testEquals() {
        UserEcp ecp1 = new UserEcp();
        assertEquals(ecp1, ecp1);
        UserEcp ecp2 = new UserEcp();
        assertNotEquals(ecp1, ecp2);
        ecp1.setId(10L);
        assertNotEquals(ecp1, ecp2);
        ecp2.setId(ecp1.getId());
        assertNotEquals(ecp1, ecp2);
        ecp1.setValue(new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        assertNotEquals(ecp1, ecp2);
        ecp2.setValue(ecp1.getValue());
        assertEquals(ecp1, ecp2);
    }
}