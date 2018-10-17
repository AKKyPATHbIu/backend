package embasa.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjUtilTest {

    @Test
    public void equals() {
        assertTrue(ObjUtil.equals("", ""));
        assertTrue(ObjUtil.equals(new String("123"), "123"));
        assertTrue(ObjUtil.equals(new Long(1000L), 1000L));
        assertTrue(ObjUtil.equals(null, null));

        assertFalse(ObjUtil.equals("", null));
        assertFalse(ObjUtil.equals(" ", null));
        assertFalse(ObjUtil.equals(new String("123"), " "));
        assertFalse(ObjUtil.equals(new String("123"), "12"));
        assertFalse(ObjUtil.equals(new Long(1000L), 300L));
        assertFalse(ObjUtil.equals(new Long(1000L), null));
    }
}