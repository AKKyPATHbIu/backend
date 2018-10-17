package embasa.validators.impl;

import embasa.validators.NumberValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberValidatorImplTest {

    NumberValidator validator = new NumberValidatorImpl();

    private Number[] greater = new Number[] { new Integer(4), new Integer(2500), new Long(250L), new Long(2L), new Double(2.5), new Double(20.22222) };
    private Number[] lesser = new Number[] { new Integer(2), new Integer(300), new Long(30L), new Long(1L), new Double(2.0), new Double(20.222) };
    private Number[] equal = new Number[] { new Integer(4), new Integer(2500), new Long(250L), new Long(2L), new Double(2.5), new Double(20.22222) };

    @Test
    public void isGreaterThan() {
        for (int i = 0; i < greater.length; i++) {
            Number greaterVal = greater[i];
            Number lesserVal = lesser[i];

            assertTrue(validator.isGreaterThan(greaterVal, lesserVal));
            assertFalse(validator.isGreaterThan(lesserVal, greaterVal));
        }
    }

    @Test
    public void isGreaterOrEquals() {
        for (int i = 0; i < greater.length; i++) {
            Number greaterVal = greater[i];
            Number lesserVal = lesser[i];
            assertTrue(validator.isGreaterOrEquals(greaterVal, greaterVal));
            assertTrue(validator.isGreaterOrEquals(greaterVal, lesserVal));
            assertFalse(validator.isGreaterOrEquals(lesserVal, greaterVal));
        }
    }

    @Test
    public void equals() {
        for (int i = 0; i < greater.length; i++) {
            Number greaterVal = greater[i];
            Number equalVal = equal[i];

            assertTrue(validator.equals(greaterVal, equalVal));
            assertTrue(validator.equals(greaterVal, greaterVal));
        }
    }

    @Test
    public void isLesserOrEquals() {
        for (int i = 0; i < lesser.length; i++) {
            Number lesserVal = lesser[i];
            Number greaterVal = greater[i];

            assertTrue(validator.isLesserOrEquals(lesserVal, greaterVal));
            assertTrue(validator.isLesserOrEquals(lesserVal, lesserVal));
        }
    }

    @Test
    public void isLesserThan() {
        for (int i = 0; i < lesser.length; i++) {
            Number lesserVal = lesser[i];
            Number greaterVal = greater[i];

            assertTrue(validator.isLesserThan(lesserVal, greaterVal));
            assertFalse(validator.isLesserThan(lesserVal, lesserVal));
        }
    }

    @Test
    public void between() {
        assertTrue(validator.between(new Integer(5), 1, 5));
        assertTrue(validator.between(new Double(10.10), 5.5, 10.11));
        assertTrue(validator.between(new Long(100L), 90L, 200L));

        assertFalse(validator.between(new Integer(50), 1, 5));
        assertFalse(validator.between(new Double(50.10), 5.5, 10.11));
        assertFalse(validator.between(new Long(300L), 90L, 200L));

        assertFalse(validator.between(new Integer(0), 1, 5));
        assertFalse(validator.between(new Double(0.10), 5.5, 10.11));
        assertFalse(validator.between(new Long(30L), 90L, 200L));
    }

    @Test
    public void isEmpty() {
        assertTrue(validator.isEmpty(null));
        assertTrue(validator.isEmpty(new Integer(0)));
        assertTrue(validator.isEmpty(new Double(0)));
        assertTrue(validator.isEmpty(new Long(0L)));
    }
}