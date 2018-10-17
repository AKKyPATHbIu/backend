package embasa.validators.impl;

import embasa.validators.BaseValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseValidatorImplTest {

    @Test
    public void isMatches() {
        BaseValidator<String> validator = new BaseValidatorImpl() {

            @Override
            public boolean isEmpty(Object validatedValue) {
                return false;
            }
        };

        assertTrue(validator.isMatches("a", "[amn]?"));
        assertTrue(validator.isMatches("aaa", "[amn]+"));
        assertTrue(validator.isMatches("aammmnn", "[amn]+"));
        assertTrue(validator.isMatches("ammmna", "[amn]*"));
        assertTrue(validator.isMatches("1", "\\d"));
        assertTrue(validator.isMatches("m", "\\D"));
        assertTrue(validator.isMatches("mad", "\\D*"));
        assertTrue(validator.isMatches("arun32", "[a-zA-Z0-9]{6}"));
        assertTrue(validator.isMatches("JA2Uk2", "[a-zA-Z0-9]{6}"));
        assertTrue(validator.isMatches("9953038949", "[789]{1}[0-9]{9}"));

        assertFalse(validator.isMatches("6953038949", "[789][0-9]{9}"));
        assertFalse(validator.isMatches("3853038949", "[789]{1}\\\\d{9}"));
        assertFalse(validator.isMatches("kkvarun32", "[a-zA-Z0-9]{6}"));
        assertFalse(validator.isMatches("arun$2", "[a-zA-Z0-9]{6}"));
        assertFalse(validator.isMatches("1", "\\D"));
        assertFalse(validator.isMatches("323abc", "\\d"));
    }
}