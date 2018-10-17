package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class WfTransitionTriggerTest {

    private String PARAMS = "params_string";

    @Test
    public void testHashCode() {
        WfTransitionTrigger tt = new WfTransitionTrigger();
        assertEquals(Objects.hash(null, null, null), tt.hashCode());
        tt.setParams(PARAMS);
        assertEquals(Objects.hash(null, null, PARAMS), tt.hashCode());

        Trigger t = new Trigger();
        t.setId(222L);
        tt.setTrigger(t);
        assertEquals(Objects.hash(null, t, PARAMS), tt.hashCode());

        tt.setTransitionId(111L);
        assertEquals(Objects.hash(111L, t, PARAMS), tt.hashCode());
    }

    @Test
    public void testEquals() {
        WfTransitionTrigger tt1 = new WfTransitionTrigger();
        WfTransitionTrigger tt2 = new WfTransitionTrigger();
        assertEquals(tt1, tt2);
        assertEquals(tt2, tt1);

        tt1.setParams(PARAMS);
        assertNotEquals(tt1, tt2);
        assertNotEquals(tt2, tt1);

        tt2.setParams(PARAMS);
        assertEquals(tt1, tt2);
        assertEquals(tt2, tt1);

        Trigger t = new Trigger();
        t.setId(2L);

        tt1.setTrigger(t);
        assertNotEquals(tt1, tt2);
        assertNotEquals(tt2, tt1);

        tt2.setTrigger(t);
        assertEquals(tt1, tt2);
        assertEquals(tt2, tt1);

        tt1.setTransitionId(12345L);
        assertNotEquals(tt1, tt2);
        assertNotEquals(tt2, tt1);
        tt2.setTransitionId(tt1.getTransitionId());
        assertEquals(tt1, tt2);
        assertEquals(tt2, tt1);
    }
}