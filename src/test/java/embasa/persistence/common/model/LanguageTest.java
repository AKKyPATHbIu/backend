package embasa.persistence.common.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class LanguageTest {

    @Test
    public void testHashCode() {
        Language l = new Language();
        assertEquals(Objects.hash(l.getLangCode()), l.hashCode());
        l.setLangCode("UA");
        assertEquals(Objects.hash(l.getLangCode()), l.hashCode());
        l.setDescription("українська");
        assertEquals(Objects.hash(l.getLangCode()), l.hashCode());
        l.setLanguage("мова");
        assertEquals(Objects.hash(l.getLangCode()), l.hashCode());
    }

    @Test
    public void testEquals() {
        Language l = new Language();
        assertEquals(l, l);
        Language l2 = new Language();
        assertNotEquals(l, l2);
        l.setLangCode("UA");
        assertNotEquals(l, l2);
        l2.setLangCode(l.getLangCode());
        assertEquals(l, l2);
        l.setLangCode("EN");
        assertNotEquals(l, l2);
    }
}