package embasa.persistence.maindb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class CardEntityValidatorTest {

    @Test
    public void test() {
        CardEntityValidator entityValidator = new CardEntityValidator();
        assertNull(entityValidator.getValidator());
        assertNull(entityValidator.getParams());

        Validator v = new Validator();
        v.setId(1L);
        v.setNameCode("name_code");

        String params = "{\"param\" : 10}";

        entityValidator.setValidator(v);
        entityValidator.setParams(params);

        assertEquals(v, entityValidator.getValidator());
        assertEquals(params, entityValidator.getParams());
    }

    @Test
    public void testHashCode() {
        CardEntityValidator entityValidator = new CardEntityValidator();
        Validator v = new Validator();
        entityValidator.setValidator(v);
        assertEquals(Objects.hash(v), entityValidator.hashCode());
        v.setDataTypeId(10L);
        assertEquals(Objects.hash(v), entityValidator.hashCode());
        v.setNameCode("name_code");
        assertEquals(Objects.hash(v), entityValidator.hashCode());
        v.setDescCode("descr_code");
        assertEquals(Objects.hash(v), entityValidator.hashCode());
        v.setId(1000L);
        assertEquals(Objects.hash(v), entityValidator.hashCode());
    }

    @Test
    public void testEquals() {
        CardEntityValidator ev1 = new CardEntityValidator();
        assertEquals(ev1, ev1);
        CardEntityValidator ev2 = new CardEntityValidator();
        assertNotEquals(ev1, ev2);

        Validator v1 = new Validator();
        ev1.setValidator(v1);
        Validator v2 = new Validator();
        ev2.setValidator(v2);
        assertNotEquals(ev1, ev2);
        ev2.setValidator(v1);
        assertEquals(ev1, ev2);

        v1.setId(30L);
        v2.setId(30L);

        ev2.setValidator(v2);
        assertEquals(ev1, ev2);

        v2.setId(777L);
        assertNotEquals(ev1, ev2);
    }
}