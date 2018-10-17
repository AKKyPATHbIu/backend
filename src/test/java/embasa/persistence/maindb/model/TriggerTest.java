package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void testListFieldsValues() {
        Trigger t = new Trigger();
        t.setId(2L);
        t.setConf("conf");
        t.setModuleId(100L);
        t.getName().setCode("name_code");
        t.getDescr().setCode("descr_code");

        assertEquals("(2,100,name_code,descr_code,conf)", t.listFieldsValues());
        t.setId(null);
        assertEquals("(0,100,name_code,descr_code,conf)", t.listFieldsValues());
        t.setConf(null);
        assertEquals("(0,100,name_code,descr_code,)", t.listFieldsValues());
        t.setModuleId(null);
        assertEquals("(0,,name_code,descr_code,)", t.listFieldsValues());
        t.getName().setCode(null);
        assertEquals("(0,,,descr_code,)", t.listFieldsValues());
        t.getDescr().setCode(null);
        assertEquals("(0,,,,)", t.listFieldsValues());
    }

    @Test
    public void testHashCode() {
        Long id = 9L;
        Trigger t = new Trigger();
        t.setId(id);
        assertEquals(t.hashCode(), id.hashCode());

        t.setId(null);
        t.setModuleId(200L);
        assertEquals(Objects.hash(t.getModuleId(), t.getName().getCode(), t.getDescr().getCode()), t.hashCode());
        t.getName().setCode("name_code");
        assertEquals(Objects.hash(t.getModuleId(), t.getName().getCode(), t.getDescr().getCode()), t.hashCode());
        t.getDescr().setCode("descr_code");
        assertEquals(Objects.hash(t.getModuleId(), t.getName().getCode(), t.getDescr().getCode()), t.hashCode());
    }

    @Test
    public void testEquals() {
        Trigger t1 = new Trigger();
        t1.setId(5L);
        Trigger t2 = new Trigger();
        t2.setId(t1.getId());
        assertEquals(t1, t2);

        t1.setId(null);
        assertNotEquals(t1, t2);
        t2.setId(null);
        assertEquals(t1, t2);
        t1.setModuleId(100L);
        assertNotEquals(t1, t2);
        t2.setModuleId(t1.getModuleId());
        assertEquals(t1, t2);

        t1.setConf("conf");
        t2.setConf(t1.getConf());
        assertEquals(t1, t2);

        t1.getName().setCode("name_code");
        t2.getName().setCode(t1.getName().getCode());
        assertEquals(t1, t2);

        t1.getDescr().setCode("descr_code");
        t2.getDescr().setCode(t1.getDescr().getCode());
        assertEquals(t1, t2);
    }
}