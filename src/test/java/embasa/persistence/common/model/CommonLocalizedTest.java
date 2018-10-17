package embasa.persistence.common.model;

import embasa.LangUtil;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class CommonLocalizedTest {

    @Test
    public void testHashCode() {
        CommonLocalized l = new CommonLocalized(LangUtil.LANG_UA);
        assertEquals(Objects.hash(LangUtil.LANG_UA.getLangCode(), l.getValue()), l.hashCode());
        l.setValue("текст");
        assertEquals(Objects.hash(LangUtil.LANG_UA.getLangCode(), l.getValue()), l.hashCode());
    }

    @Test
    public void testEquals() {
        CommonLocalized l = new CommonLocalized(LangUtil.LANG_UA);
        assertEquals(l, l);
        assertNotEquals(l, new Object());
        assertNotEquals(l,null);
        CommonLocalized l2 = new CommonLocalized(LangUtil.LANG_UA);
        assertEquals(l, l2);
        l.setValue("текст");
        assertNotEquals(l, l2);
        l2.setValue(l.getValue());
        assertEquals(l, l2);
    }
}