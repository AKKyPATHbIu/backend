package embasa.persistence;

import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcUtilTest {

    @Test
    public void escapeQuoters() {
        String testStr[] = new String[] { "{\"ttt\" : 1}", "{\"field\" : \"some value\", { \"json\", \"json value\" } }" };
        String expectedStr[] = new String[] {"{\\\"ttt\\\" : 1}", "{\\\"field\\\" : \\\"some value\\\", { \\\"json\\\", \\\"json value\\\" } }"};

        for (int i = 0;i < testStr.length; i++) {
            String testVal = testStr[i];
            String expectedVal = expectedStr[i];
            assertEquals(expectedVal, JdbcUtil.escapeQuoters(testVal));
        }
    }

    @Test
    public void objToStr() {
        Long value = null;
        Object[] objects = new Object[] { new Integer(5), "some string", null, "  ", new Long(100L), value };
        String[] expected = new String[] {"5", "some string", "", "  ", "100", ""};
        for (int i = 0; i < objects.length; i++) {
            assertEquals(expected[i], JdbcUtil.objToStr(objects[i]));
        }
    }
}