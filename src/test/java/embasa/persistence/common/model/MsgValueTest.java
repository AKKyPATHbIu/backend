package embasa.persistence.common.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class MsgValueTest {

    @Test
    public void testHashCode() {
        MsgValue msgValue = new MsgValue();
        assertEquals(Objects.hash(msgValue.getCode(), msgValue.getLangCode()), msgValue.hashCode());
        msgValue.setCode("code");
        assertEquals(Objects.hash(msgValue.getCode(), msgValue.getLangCode()), msgValue.hashCode());
        msgValue.setLangCode("en");
        assertEquals(Objects.hash(msgValue.getCode(), msgValue.getLangCode()), msgValue.hashCode());
    }

    @Test
    public void testEquals() {
        MsgValue msgValue = new MsgValue();
        assertEquals(msgValue, msgValue);
        MsgValue msgValue1 = new MsgValue();
        assertNotEquals(msgValue, msgValue1);
        msgValue.setCode("code");
        assertNotEquals(msgValue, msgValue1);
        msgValue.setLangCode("ua");
        assertNotEquals(msgValue, msgValue1);
        msgValue1.setCode(msgValue.getCode());
        assertNotEquals(msgValue, msgValue1);
        msgValue1.setLangCode(msgValue.getLangCode());
        assertEquals(msgValue, msgValue1);
    }
}