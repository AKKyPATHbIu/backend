package embasa.i18n;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class LocaleUtilTest {

    @Test
    public void parserLocaleBy() {
        assertEquals(LocaleUtil.LOCALE_UA, LocaleUtil.parseLocaleBy(" ua"));
        assertEquals(LocaleUtil.LOCALE_UA, LocaleUtil.parseLocaleBy("uA "));
        assertEquals(LocaleUtil.LOCALE_UA, LocaleUtil.parseLocaleBy("  Ua "));
        assertEquals(LocaleUtil.LOCALE_UA, LocaleUtil.parseLocaleBy("  UA  "));
        assertEquals(LocaleUtil.LOCALE_UA, LocaleUtil.parseLocaleBy(""));
        assertEquals(LocaleUtil.LOCALE_UA, LocaleUtil.parseLocaleBy(null));

        assertEquals(LocaleUtil.LOCALE_RU, LocaleUtil.parseLocaleBy("  ru  "));
        assertEquals(LocaleUtil.LOCALE_RU, LocaleUtil.parseLocaleBy("rU   "));
        assertEquals(LocaleUtil.LOCALE_RU, LocaleUtil.parseLocaleBy("   Ru  "));
        assertEquals(LocaleUtil.LOCALE_RU, LocaleUtil.parseLocaleBy("  RU   "));

        assertEquals(Locale.ENGLISH, LocaleUtil.parseLocaleBy("  en  "));
        assertEquals(Locale.ENGLISH, LocaleUtil.parseLocaleBy(" eN "));
        assertEquals(Locale.ENGLISH, LocaleUtil.parseLocaleBy("  En   "));
        assertEquals(Locale.ENGLISH, LocaleUtil.parseLocaleBy(" EN "));
    }
}