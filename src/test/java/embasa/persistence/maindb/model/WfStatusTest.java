package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class WfStatusTest {

    @Test
    public void testHashCode() {
        WfStatus s = new WfStatus();
        assertEquals(Objects.hash(s.getClinicId(), s.getName(), s.getDescr()), s.hashCode());
        s.setId(5L);
        assertEquals((new Long(5L)).hashCode(), s.hashCode());
        s.setClinicId(100L);
        assertEquals((new Long(5L)).hashCode(), s.hashCode());
        s.setId(null);
        assertNotEquals((new Long(5L)).hashCode(), s.hashCode());
        assertEquals(Objects.hash(s.getClinicId(), s.getName(), s.getDescr()), s.hashCode());
        s.setName("some_name");
        assertEquals(Objects.hash(s.getClinicId(), s.getName(), s.getDescr()), s.hashCode());
        s.setDescr("descr");
        assertEquals(Objects.hash(s.getClinicId(), s.getName(), s.getDescr()), s.hashCode());
    }

    @Test
    public void testEquals() {
        WfStatus s1 = new WfStatus();
        assertEquals(s1, s1);
        assertNotEquals(s1, new Object());
        s1.setId(10L);
        WfStatus s2 = new WfStatus();
        s2.setId(10L);
        assertEquals(s1, s2);

        s1.setId(null);
        s2.setId(null);

        assertNotEquals(s1, s2);

        s1.setName("name1");
        s2.setName("name2");
        assertNotEquals(s1, s2);
        s2.setName(s1.getName());
        assertNotEquals(s1, s2);

        s1.setClinicId(30L);
        assertNotEquals(s1, s2);
        s2.setClinicId(s1.getClinicId());
        assertEquals(s1, s2);
        s1.setDescr("descr");
        assertNotEquals(s1, s2);
        s2.setDescr(s1.getDescr());
        assertEquals(s1, s2);
    }

    @Test
    public void test() {
        WfStatus wStatus = new WfStatus();
        assertNull(wStatus.getClinic());
        assertEquals(0, wStatus.getTriggers().size());
        assertEquals(0, wStatus.getValidators().size());

        Clinic c = new Clinic();
        wStatus.setClinic(c);
        assertEquals(c, wStatus.getClinic());
    }
}