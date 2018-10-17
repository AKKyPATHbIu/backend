package embasa.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static embasa.util.DateUtil.*;
import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void testGetToday() {
        Date now = truncate(new Date());
        assertEquals(now, getToday());
    }

    @Test
    public void testGetTomorrow() {
        Date tomorrow = shiftDate(getToday(), Calendar.DAY_OF_MONTH, 1);
        assertEquals(tomorrow, getTomorrow());
    }

    @Test
    public void testGetYesterday() {
        Date yesterday = shiftDate(getToday(), Calendar.DAY_OF_MONTH, -1);
        assertEquals(yesterday, getYesterday());
    }

    @Test
    public void testShiftDate() {
        Date now = new Date();

        Date shifted = shiftDate(now, Calendar.DAY_OF_MONTH, -10);
        assertTrue(now.after(shifted));
        shifted = shiftDate(shifted, Calendar.DAY_OF_MONTH, 10);
        assertEquals(now, shifted);

        shifted = shiftDate(now, Calendar.YEAR, -1);
        assertTrue(now.after(shifted));
        shifted = shiftDate(shifted, Calendar.YEAR, 1);
        assertEquals(now, shifted);

        Calendar calendar = toCalendar(now);
        calendar.add(Calendar.MONTH, 3);

        assertEquals(calendar.getTime(), shiftDate(now, Calendar.MONTH, 3));
    }

    @Test
    public void testTruncate() {
        Date now = new Date();
        Calendar calendar = toCalendar(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date truncated = truncate(now);
        assertEquals(calendar.getTime(), truncated);
        assertTrue(truncated.before(now));
    }

    @Test
    public void testToCalendar() {
        Date now = new Date();
        Calendar calendar = toCalendar(now);
        assertEquals(now, calendar.getTime());
    }
}