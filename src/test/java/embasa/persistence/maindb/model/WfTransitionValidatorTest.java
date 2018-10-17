package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class WfTransitionValidatorTest {

    private String PARAMS = "params";

    @Test
    public void testHashCode() {
        WfTransitionValidator tv = new WfTransitionValidator();
        assertEquals(Objects.hash(null, null, null), tv.hashCode());
        tv.setParams(PARAMS);
        assertEquals(Objects.hash(null, null, PARAMS), tv.hashCode());

        Validator v = new Validator();
        v.setDataTypeId(10L);
        v.setId(20L);
        tv.setValidator(v);

        assertEquals(Objects.hash(null, v, PARAMS), tv.hashCode());
        tv.setTransitionId(333L);
        assertEquals(Objects.hash(333L, v, PARAMS), tv.hashCode());
    }

    @Test
    public void testEquals() {
        WfTransitionValidator tv1 = new WfTransitionValidator();
        WfTransitionValidator tv2 = new WfTransitionValidator();
        assertEquals(tv1, tv2);
        assertEquals(tv2, tv1);

        tv1.setParams(PARAMS);
        assertNotEquals(tv1, tv2);
        assertNotEquals(tv2, tv1);
        tv2.setParams(tv1.getParams());
        assertEquals(tv1, tv2);
        assertEquals(tv2, tv1);

        Validator v = new Validator();
        v.setId(999L);

        tv1.setValidator(v);
        assertNotEquals(tv1, tv2);
        assertNotEquals(tv2, tv1);
        tv2.setValidator(v);
        assertEquals(tv1, tv2);
        assertEquals(tv2, tv1);

        tv1.setTransitionId(100L);
        assertNotEquals(tv1, tv2);
        assertNotEquals(tv2, tv1);
        tv2.setTransitionId(tv1.getTransitionId());
        assertEquals(tv1, tv2);
        assertEquals(tv2, tv1);
    }
}