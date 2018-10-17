package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class WfTransitionTest {

    @Test
    public void testHashCode() {
        WfTransition t = new WfTransition();
        assertEquals(Objects.hash(t.getEntityId(), t.getStatusId(), t.getNextStatusId(), t.getLevel()), t.hashCode());
        t.setEntityId(7L);
        assertEquals(Objects.hash(t.getEntityId(), t.getStatusId(), t.getNextStatusId(), t.getLevel()), t.hashCode());
        t.setNextStatusId(111L);
        assertEquals(Objects.hash(t.getEntityId(), t.getStatusId(), t.getNextStatusId(), t.getLevel()), t.hashCode());
        t.setStatusId(12345L);
        assertEquals(Objects.hash(t.getEntityId(), t.getStatusId(), t.getNextStatusId(), t.getLevel()), t.hashCode());
        t.setId(999L);
        assertEquals((new Long(999L)).hashCode(), t.hashCode());
    }

    @Test
    public void testEquals() {
        WfTransition t1 = new WfTransition();
        assertEquals(t1, t1);
        assertNotEquals(t1, new Object());

        WfTransition t2 = new WfTransition();
        assertNotEquals(t1, t2);

        t1.setStatusId(2L);
        assertNotEquals(t1, t2);

        t2.setStatusId(t1.getStatusId());
        assertEquals(t1, t2);

        t1.setEntityId(1L);
        assertNotEquals(t1, t2);

        t2.setEntityId(t1.getEntityId());
        assertEquals(t1, t2);

        t1.setNextStatusId(3L);
        assertNotEquals(t1, t2);

        t2.setNextStatusId(t1.getNextStatusId());
        assertEquals(t1, t2);

        t1.setId(10L);
        assertNotEquals(t1, t2);

        t2.setId(t1.getId());
        assertEquals(t1, t2);
    }

    @Test
    public void listFieldsValues() {
        WfTransition t1 = new WfTransition();
        assertEquals("null,null,null,null,ARRAY[]::wf_transition_validators[],ARRAY[]::wf_transition_triggers[]", t1.listFieldsValues());
        t1.setEntityId(1L);
        t1.setStatusId(2L);
        t1.setNextStatusId(3L);
        t1.setLevel(4);
        t1.setId(1000L);
        assertEquals("1,2,3,4,ARRAY[]::wf_transition_validators[],ARRAY[]::wf_transition_triggers[]", t1.listFieldsValues());
    }

    @Test
    public void test() {
        WfTransition t1 = new WfTransition();
        assertNull(t1.getEntity());
        assertNull(t1.getNextStatus());
        assertNull(t1.getStatus());

        CardEntity cardEntity = new CardEntity();
        WfStatus wfStatus = new WfStatus();
        WfStatus wfNextStatus = new WfStatus();

        t1.setEntity(cardEntity);
        t1.setStatus(wfStatus);
        t1.setNextStatus(wfNextStatus);

        assertEquals(cardEntity, t1.getEntity());
        assertEquals(wfStatus, t1.getStatus());
        assertEquals(wfNextStatus, t1.getNextStatus());
    }
}