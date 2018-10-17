package embasa.persistence.securedb.model;

import embasa.LangUtil;
import embasa.persistence.common.model.CommonLocalized;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AcskTest {

    /** Тестовий текст для української мови. */
    private final String TEXT_UA = "українська";

    /** Тестовий текст для російської мови. */
    private final String TEXT_RU = "русский";

    /** Тестовий текст для англійської мови. */
    private final String TEXT_EN = "English";

    @Test
    public void getResource() {
        Acsk acsk = new Acsk();
        acsk.setNameCode("resource");

        CommonLocalized loc = new CommonLocalized(LangUtil.LANG_UA);
        loc.setValue(TEXT_UA);
        acsk.addNameResource(loc);

        loc = new CommonLocalized(LangUtil.LANG_RU);
        loc.setValue(TEXT_RU);
        acsk.addNameResource(loc);

        loc = new CommonLocalized(LangUtil.LANG_EN);
        loc.setValue(TEXT_EN);
        acsk.addNameResource(loc);

        assertEquals(TEXT_UA, acsk.getName(LangUtil.LANG_UA));
        assertEquals(TEXT_RU, acsk.getName(LangUtil.LANG_RU));
        assertEquals(TEXT_EN, acsk.getName(LangUtil.LANG_EN));
    }

    @Test
    public void testHashCode() {
        Acsk acsk = new Acsk();
        assertEquals(Objects.hash(acsk.getNameCode()), acsk.hashCode());
        acsk.setNameCode("name_code");
        assertEquals(Objects.hash(acsk.getNameCode()), acsk.hashCode());
        acsk.setId(100L);
        assertEquals(acsk.getId().hashCode(), acsk.hashCode());
    }

    @Test
    public void testEquals() {
        Acsk acsk1 = new Acsk();
        assertEquals(acsk1, acsk1);

        Acsk acsk2 = new Acsk();
        assertNotEquals(acsk1, acsk2);

        acsk1.setNameCode("test_code");
        assertNotEquals(acsk1, acsk2);
        acsk2.setNameCode(acsk1.getNameCode());
        assertEquals(acsk1, acsk2);

        acsk1.setId(123L);
        assertNotEquals(acsk1, acsk2);
        acsk2.setId(acsk1.getId());
        assertEquals(acsk1, acsk2);
    }
}