package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void test() {
        Validator v = new Validator();
        assertNull(v.getRule());
        assertNull(v.getDataTypeId());
        String rule = "rule";
        Long dataTypeId = 111111L;
        v.setRule(rule);
        v.setDataTypeId(dataTypeId);
        assertEquals(rule, v.getRule());
        assertEquals(dataTypeId, v.getDataTypeId());
    }

    @Test
    public void testHashCode() {
        Validator v = new Validator();
        assertEquals(Objects.hash(v.getDataTypeId(), v.getNameCode(), v.getDescCode()), v.hashCode());
        v.setNameCode("name_code");
        assertEquals(Objects.hash(v.getDataTypeId(), v.getNameCode(), v.getDescCode()), v.hashCode());
        v.setDescCode("desc_code");
        assertEquals(Objects.hash(v.getDataTypeId(), v.getNameCode(), v.getDescCode()), v.hashCode());
        v.setId(11L);
        assertEquals(v.getId().hashCode(), v.hashCode());
    }

    @Test
    public void testEquals() {
        Validator v1 = new Validator();
        assertEquals(v1, v1);
        Validator v2 = new Validator();
        assertNotEquals(v1, v2);
        v1.setId(10L);
        assertNotEquals(v1, v2);
        v2.setId(v1.getId());
        assertEquals(v1, v2);
        v1.setDataTypeId(111L);
        assertNotEquals(v1, v2);
        v2.setDataTypeId(v1.getDataTypeId());
        assertEquals(v1, v2);
        v1.setNameCode("name_code");
        assertNotEquals(v1, v2);
        v2.setNameCode(v1.getNameCode());
        assertEquals(v1, v2);
        v1.setDescCode("desc_code");
        assertNotEquals(v1, v2);
        v2.setDescCode(v1.getDescCode());
        assertEquals(v1, v2);
        assertNotEquals(v1, null);
    }

    @Test
    public void listFieldsValues() {
        Validator v = new Validator();
        assertEquals("(0,,,,)", v.listFieldsValues());
        v.setId(111L);
        v.setRule("rule");
        v.setDataTypeId(222L);
        v.setNameCode("name_code");
        v.setDescCode("desc_code");
        assertEquals("(111,222,name_code,desc_code,rule)", v.listFieldsValues());
    }
}