package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class CardEntityAttrTest {

    @Test
    public void listFieldsValues() {
        CardEntityAttr attr = new CardEntityAttr();
        assertEquals("(0,,,,,FALSE,,,,,FALSE,FALSE,)", attr.listFieldsValues());
        attr.setId(10L);
        attr.setConstrEntityId(20L);
        attr.setName("name");
        attr.setType(30L);
        attr.setDescr("descr");
        attr.setAudited(true);
        attr.setPosition(40L);
        attr.setRefConstrEntityId(50L);
        attr.setUiConf("{\"zzz\" : 1}");
        attr.setFieldName("fieldname");
        attr.setNullable(true);
        attr.setIndexed(true);
        attr.setStatus(60L);
        assertEquals("(10,20,name,30,descr,TRUE,40,50,{\\\"zzz\\\" : 1},fieldname,TRUE,TRUE,60)", attr.listFieldsValues());
    }

    @Test
    public void testHashCode() {
        CardEntityAttr ea = new CardEntityAttr();
        assertEquals(Objects.hash(ea.getId(), ea.getConstrEntityId()), ea.hashCode());
        ea.setId(11L);
        assertEquals(Objects.hash(ea.getId(), ea.getConstrEntityId()), ea.hashCode());
        ea.setConstrEntityId(22L);
        assertEquals(Objects.hash(ea.getId(), ea.getConstrEntityId()), ea.hashCode());
    }

    @Test
    public void testEquals() {
        CardEntityAttr ea = new CardEntityAttr();
        assertEquals(ea, ea);
        assertNotEquals(ea, new Object());
        CardEntityAttr ea1 = new CardEntityAttr();
        assertNotEquals(ea, ea1);
        ea.setConstrEntityId(100L);
        assertNotEquals(ea, ea1);
        ea1.setConstrEntityId(ea.getConstrEntityId());
        assertEquals(ea, ea1);
        ea.setName("name");
        assertNotEquals(ea, ea1);
        ea1.setName(ea.getName());
        assertEquals(ea, ea1);
        ea.setType(200L);
        assertNotEquals(ea, ea1);
        ea1.setType(ea.getType());
        assertEquals(ea , ea1);
        ea.setRefConstrEntityId(333L);
        assertNotEquals(ea, ea1);
        ea1.setRefConstrEntityId(ea.getRefConstrEntityId());
        assertEquals(ea, ea1);
        ea.setFieldName("field_name");
        assertNotEquals(ea, ea1);
        ea1.setFieldName(ea.getFieldName());
        assertEquals(ea, ea1);
        ea.setId(4444L);
        assertNotEquals(ea, ea1);
        ea1.setId(ea.getId());
        assertEquals(ea, ea1);
    }

    @Test
    public void test() {
        List<CardEntityValidator> validators = new ArrayList<> ();
        CardEntityValidator cv = new CardEntityValidator();

        Validator v  = new Validator();
        v.setId(10L);
        v.setNameCode("name_code1");
        v.setNameCode("descr_code1");
        cv.setValidator(v);
        validators.add(cv);

        cv = new CardEntityValidator();
        v  = new Validator();
        v.setId(11L);
        v.setNameCode("name_code2");
        v.setNameCode("descr_code2");
        cv.setValidator(v);
        validators.add(cv);

        cv = new CardEntityValidator();
        v  = new Validator();
        v.setId(12L);
        v.setNameCode("name_code3");
        v.setNameCode("descr_code3");
        cv.setValidator(v);
        validators.add(cv);

        CardEntityAttr ea = new CardEntityAttr();
        List<CardEntityValidator> l = ea.getValidators();
        assertEquals(0, l.size());
        ea.setValidators(validators);
        assertEquals(validators.size(), l.size());
        assertTrue(l.containsAll(validators));
    }
}