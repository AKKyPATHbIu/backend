package embasa.versioning;

import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VersionParserImplTest {

    @Mock
    Localizer localizer;

    @InjectMocks
    VersionParser versionParser = new VersionParserImpl();

    private final Locale locale = LocaleUtil.LOCALE_UA;

    @Test
    public void parse() {
        when(localizer.getMessage("versionIsEmpty", locale)).thenReturn("Не задано код версії продукту!");
        when(localizer.getMessage("badVersionStructure", locale)).thenReturn("Помилкова структура версії продукту (%s)!");
        checkEmpty();
        checkBadStructure();
        checkGoodStructure();
    }

    private void checkEmpty() {
        String errMsg = "Не задано код версії продукту!";
        try {
            versionParser.parse("", locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(errMsg, ex.getMessage());
        }

        try {
            versionParser.parse("  ", locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(errMsg, ex.getMessage());
        }

        try {
            versionParser.parse(null, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(errMsg, ex.getMessage());
        }
    }

    private void checkBadStructure() {
        String errMsg = "Помилкова структура версії продукту (%s)!";

        String version = "100";
        try {
            versionParser.parse(version, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }

        version = "asd";
        try {
            versionParser.parse(version, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }

        version = "10.10";
        try {
            versionParser.parse("10.10", locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }

        version = "1.10.aaa";
        try {
            versionParser.parse(version, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }

        version = "zzzz.w.aaa";
        try {
            versionParser.parse(version, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }

        version = ".w.aaa";
        try {
            versionParser.parse(version, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }

        version = "..";
        try {
            versionParser.parse(version, locale);
        } catch (IllegalArgumentException ex) {
            assertEquals(String.format(errMsg, version), ex.getMessage());
        }
    }

    private void checkGoodStructure() {
        Map<String, Version> map = new HashMap<> ();
        map.put("100.1.1", new VersionImpl(100, 1,1));
        map.put("0.0.1", new VersionImpl(0, 0,1));
        map.put("0.10.102", new VersionImpl(0, 10,102));
        map.put("1.1.1", new VersionImpl(1, 1,1));
        map.put("325.123.111", new VersionImpl(325, 123,111));

        for (Map.Entry<String, Version> ent : map.entrySet()) {
            String key = ent.getKey();
            Version version = ent.getValue();

            assertTrue(versionParser.parse(key, locale).equals(version));
        }
    }
}