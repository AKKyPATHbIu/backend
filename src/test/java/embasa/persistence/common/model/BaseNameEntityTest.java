package embasa.persistence.common.model;

import embasa.LangUtil;
import embasa.persistence.common.LocalizedList;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseNameEntityTest {

    @Test
    public void test() {
        BaseNameEntity<Long> e = new BaseNameEntity<> ();
        String nameCode = "name_code";
        e.setNameCode(nameCode);
        assertEquals(nameCode, e.getNameCode());

        LocalizedList nameList = e.getName();
        assertEquals(0, nameList.size());

        CommonLocalized locUA = new CommonLocalized(LangUtil.LANG_UA);
        locUA.setValue("українська");

        CommonLocalized locRU = new CommonLocalized(LangUtil.LANG_RU);
        locRU.setValue("русский");

        CommonLocalized locEN = new CommonLocalized(LangUtil.LANG_EN);
        locEN.setValue("English");

        e.addNameResource(locUA);
        e.addNameResource(locRU);
        e.addNameResource(locEN);
        assertEquals(3, nameList.size());

        assertEquals(locUA.getValue(), e.getNameResource(LangUtil.LANG_UA));
        assertEquals(locRU.getValue(), e.getNameResource(LangUtil.LANG_RU));
        assertEquals(locEN.getValue(), e.getNameResource(LangUtil.LANG_EN));
    }
}