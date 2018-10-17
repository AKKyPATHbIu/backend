package embasa.validators.impl;

import embasa.validators.BaseValidator;

/** Реалізація базового валідатора. */
public abstract class BaseValidatorImpl<T> implements BaseValidator<T> {

    @Override
    public boolean isMatches(String validatedValue, String regExp) {
        return validatedValue != null && validatedValue.matches(regExp);
    }
}
