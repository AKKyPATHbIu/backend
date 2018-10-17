package embasa.validators.impl;

import embasa.validators.DateValidator;

import java.util.Calendar;
import java.util.Date;

import static embasa.util.DateUtil.getToday;
import static embasa.util.DateUtil.toCalendar;
import static embasa.util.DateUtil.truncate;

/** Реалізація валідатора дат. */
public class DateValidatorImpl extends BaseValidatorImpl<Date> implements DateValidator<Date> {

    @Override
    public boolean isToday(Date validatedValue) {
        return !isEmpty(validatedValue) && truncate(validatedValue).equals(getToday());
    }

    @Override
    public boolean before(Date validatedValue, Date date) {
        return !isEmpty(validatedValue) && !isEmpty(date) && validatedValue.before(date);
    }

    @Override
    public boolean beforeWithoutTime(Date validatedValue, Date date) {
        if (isEmpty(validatedValue) || isEmpty(date)) {
            return false;
        }

        return before(truncate(validatedValue), truncate(date));
    }

    @Override
    public boolean after(Date validatedValue, Date date) {
        return !isEmpty(validatedValue) && !isEmpty(date) && validatedValue.after(date);
    }

    @Override
    public boolean afterWithoutTime(Date validatedValue, Date date) {
        if (isEmpty(validatedValue) || isEmpty(date)) {
            return false;
        }

        return after(truncate(validatedValue), truncate(date));
    }

    @Override
    public boolean beforeTodayMinus(Date validatedValue, int calendarField, int amount) {
        if (isEmpty(validatedValue)) {
            return false;
        }
        Calendar calendar = toCalendar(getToday());
        calendar.add(calendarField, -amount);
        return before(validatedValue, calendar.getTime());
    }

    @Override
    public boolean isEmpty(Date validatedValue) {
        return validatedValue == null;
    }
}
