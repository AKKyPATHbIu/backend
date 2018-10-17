package embasa.persistence.common.model;

import embasa.LangUtil;
import embasa.persistence.common.LocalizedList;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseNameDescEntityTest {

    @Test
    public void test() {
        BaseNameDescEntity<Long> e = new BaseNameDescEntity<> ();
        LocalizedList descr = e.getDescr();
        assertEquals(0, descr.size());

        CommonLocalized locUA = new CommonLocalized(LangUtil.LANG_UA);
        locUA.setValue("українська");

        CommonLocalized locRU = new CommonLocalized(LangUtil.LANG_RU);
        locRU.setValue("русский");

        CommonLocalized locEN = new CommonLocalized(LangUtil.LANG_EN);
        locEN.setValue("English");

        e.addDescResource(locUA);
        e.addDescResource(locRU);
        e.addDescResource(locEN);

        assertEquals(3, descr.size());
        assertEquals(locUA.getValue(), e.getDescResource(LangUtil.LANG_UA));
        assertEquals(locRU.getValue(), e.getDescResource(LangUtil.LANG_RU));
        assertEquals(locEN.getValue(), e.getDescResource(LangUtil.LANG_EN));
    }
}