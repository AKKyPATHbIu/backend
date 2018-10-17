package embasa.validators.impl;

import embasa.validators.DateValidator;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static embasa.util.DateUtil.toCalendar;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateValidatorImplTest {

    DateValidator validator = new DateValidatorImpl();
    Calendar calendar = toCalendar(new Date());

    @Test
    public void isToday() {
        Date today = new Date();

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        Date yesterday = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 2);

        Date tomorrow = calendar.getTime();

        assertTrue(validator.isToday(today));
        assertFalse(validator.isToday(yesterday));
        assertFalse(validator.isToday(tomorrow));
    }

    @Test
    public void before() {
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        assertTrue(validator.before(calendar.getTime(), today));
        calendar.add(Calendar.MONTH, -2);
        assertTrue(validator.before(calendar.getTime(), today));
        calendar.add(Calendar.YEAR, -10);
        assertTrue(validator.before(calendar.getTime(), today));

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(validator.before(calendar.getTime(), today));
        calendar.add(Calendar.MONTH, 1);
        assertFalse(validator.before(calendar.getTime(), today));
        calendar.add(Calendar.YEAR, 1);
        assertFalse(validator.before(calendar.getTime(), today));

        calendar.setTime(today);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        assertTrue(validator.before(today, calendar.getTime()));
    }

    @Test
    public void beforeWithoutTime() {
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        assertTrue(validator.beforeWithoutTime(calendar.getTime(), today));
        calendar.add(Calendar.MONTH, -2);
        assertTrue(validator.beforeWithoutTime(calendar.getTime(), today));
        calendar.add(Calendar.YEAR, -10);
        assertTrue(validator.beforeWithoutTime(calendar.getTime(), today));

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(validator.beforeWithoutTime(calendar.getTime(), today));
        calendar.add(Calendar.MONTH, 1);
        assertFalse(validator.beforeWithoutTime(calendar.getTime(), today));
        calendar.add(Calendar.YEAR, 1);
        assertFalse(validator.beforeWithoutTime(calendar.getTime(), today));

        calendar.setTime(today);
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        assertFalse(validator.beforeWithoutTime(today, calendar.getTime()));
    }

    @Test
    public void after() {
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        assertTrue(validator.after(today, calendar.getTime()));
        calendar.add(Calendar.MONTH, -2);
        assertTrue(validator.after(today, calendar.getTime()));
        calendar.add(Calendar.YEAR, -10);
        assertTrue(validator.after(today, calendar.getTime()));

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(validator.after(today, calendar.getTime()));
        calendar.add(Calendar.MONTH, 1);
        assertFalse(validator.after(today, calendar.getTime()));
        calendar.add(Calendar.YEAR, 1);
        assertFalse(validator.after(today, calendar.getTime()));

        calendar.setTime(today);
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        assertTrue(validator.after(calendar.getTime(), today));
    }

    @Test
    public void afterWithoutTime() {
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        assertTrue(validator.afterWithoutTime(today, calendar.getTime()));
        calendar.add(Calendar.MONTH, -2);
        assertTrue(validator.afterWithoutTime(today, calendar.getTime()));
        calendar.add(Calendar.YEAR, -10);
        assertTrue(validator.afterWithoutTime(today, calendar.getTime()));

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(validator.afterWithoutTime(today, calendar.getTime()));
        calendar.add(Calendar.MONTH, 1);
        assertFalse(validator.afterWithoutTime(today, calendar.getTime()));
        calendar.add(Calendar.YEAR, 1);
        assertFalse(validator.afterWithoutTime(today, calendar.getTime()));

        calendar.setTime(today);
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        assertFalse(validator.afterWithoutTime(calendar.getTime(), today));
    }

    @Test
    public void beforeTodayMinus() {
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -5);

        assertTrue(validator.beforeTodayMinus(calendar.getTime(), Calendar.DAY_OF_MONTH, 3));

        calendar.setTime(today);
        calendar.add(Calendar.MONTH, -2);

        assertTrue(validator.beforeTodayMinus(calendar.getTime(), Calendar.MONTH, 1));

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        assertFalse(validator.beforeTodayMinus(calendar.getTime(), Calendar.DAY_OF_MONTH, 5));
        assertFalse(validator.beforeTodayMinus(today, Calendar.DAY_OF_MONTH, 2));
    }

    @Test
    public void isEmpty() {
        assertTrue(validator.isEmpty(null));
        assertFalse(validator.isEmpty(new Date()));
    }
}