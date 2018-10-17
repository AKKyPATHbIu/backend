package embasa.validators.impl;

import embasa.validators.StringValidator;
import org.springframework.util.StringUtils;

/** Реалізація строкового валідатора. */
public class StringValidatorImpl extends BaseValidatorImpl<java.lang.String> implements StringValidator<java.lang.String> {

    @Override
    public boolean isLengthNotLesserThan(String validatedValue, int length) {
        return StringUtils.hasText(validatedValue) && validatedValue.length() >= length;
    }

    @Override
    public boolean isLengthNotGreaterThan(String validatedValue, int length) {
        return validatedValue == null  ? length >= 1 : validatedValue.trim().length() <= length;
    }

    @Override
    public boolean isEmpty(String validatedValue) {
        return !StringUtils.hasText(validatedValue);
    }
}
