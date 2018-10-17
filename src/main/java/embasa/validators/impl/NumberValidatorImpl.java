package embasa.validators.impl;

import embasa.validators.NumberValidator;

/** Числовий валідатор. */
public class NumberValidatorImpl extends BaseValidatorImpl<Number> implements NumberValidator<Number> {

    @Override
    public boolean isGreaterThan(Number validatedValue, Number value) {
        return validatedValue.doubleValue() > value.doubleValue();
    }

    @Override
    public boolean isGreaterOrEquals(Number validatedValue, Number value) {
        return validatedValue.doubleValue() >= value.doubleValue();
    }

    @Override
    public boolean equals(Number validatedValue, Number value) {
        return validatedValue.doubleValue() == value.doubleValue();
    }

    @Override
    public boolean isLesserOrEquals(Number validatedValue, Number value) {
        return validatedValue.doubleValue() <= value.doubleValue();
    }

    @Override
    public boolean isLesserThan(Number validatedValue, Number value) {
        return validatedValue.doubleValue() < value.doubleValue();
    }

    @Override
    public boolean between(Number validatedValue, Number fromValue, Number toValue) {
        return validatedValue.doubleValue() >= fromValue.doubleValue() && validatedValue.doubleValue() <= toValue.doubleValue();
    }

    @Override
    public boolean isEmpty(Number validatedValue) {
        return validatedValue == null || validatedValue.doubleValue() == 0;
    }
}
