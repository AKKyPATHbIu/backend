package embasa.validators.impl;

import embasa.validators.StringValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringValidatorImplTest {

    private StringValidator validator = new StringValidatorImpl();

    @Test
    public void hasMinLength() {
        assertTrue(validator.isLengthNotLesserThan("text", 1));
        assertTrue(validator.isLengthNotLesserThan("text", 2));
        assertTrue(validator.isLengthNotLesserThan("text", 3));
        assertTrue(validator.isLengthNotLesserThan("text", 4));
        assertFalse(validator.isLengthNotLesserThan("text", 5));
        assertFalse(validator.isLengthNotLesserThan("text", 6));
        assertFalse(validator.isLengthNotLesserThan("text", 7));
        assertFalse(validator.isLengthNotLesserThan("text", 10));
        assertFalse(validator.isLengthNotLesserThan("text", 100));

        assertFalse(validator.isLengthNotLesserThan("", 5));
        assertFalse(validator.isLengthNotLesserThan(null, 50));
        assertFalse(validator.isLengthNotLesserThan("   ", 2));
    }

    @Test
    public void hasMaxLength() {
        assertFalse(validator.isLengthNotGreaterThan("text", 1));
        assertFalse(validator.isLengthNotGreaterThan("text", 2));
        assertFalse(validator.isLengthNotGreaterThan("text", 3));
        assertTrue(validator.isLengthNotGreaterThan("text", 4));
        assertTrue(validator.isLengthNotGreaterThan("text", 5));
        assertTrue(validator.isLengthNotGreaterThan("text", 6));
        assertTrue(validator.isLengthNotGreaterThan("text", 7));
        assertTrue(validator.isLengthNotGreaterThan("text", 10));
        assertTrue(validator.isLengthNotGreaterThan("text", 100));

        assertTrue(validator.isLengthNotGreaterThan("", 2));
        assertTrue(validator.isLengthNotGreaterThan("     ", 3));
        assertTrue(validator.isLengthNotGreaterThan(null, 5));
    }

    @Test
    public void isEmpty() {
        assertTrue(validator.isEmpty(null));
        assertTrue(validator.isEmpty(""));
        assertTrue(validator.isEmpty("     "));

        assertFalse(validator.isEmpty("1"));
        assertFalse(validator.isEmpty("asgfshg"));
        assertFalse(validator.isEmpty("eef  ef ee"));
    }
}