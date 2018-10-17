package embasa.util;

import java.util.Calendar;
import java.util.Date;

/** Допоміжна утиліта для роботи з датами. */
public class DateUtil {

    /**
     * Отримати поточну дату
     * @return поточна дата
     */
    public static Date getToday() {
        return truncate(new Date());
    }

    /**
     * Отримати завтрашню дату
     * @return завтрашня дата
     */
    public static Date getTomorrow() {
        return shiftDate(getToday(), Calendar.DAY_OF_MONTH, 1);
    }

    /**
     * Отримати вчорашню дату
     * @return вчорашня дата
     */
    public static Date getYesterday() {
        return shiftDate(getToday(), Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * Змістити дату
     * @param date дата
     * @param periodType тип періода {@link Calendar}
     * @param amount значення, на яке необхідно змістити дату
     * @return дата +/- ammount днів, тижнів, місяців тощо.
     */
    public static Date shiftDate(Date date, int periodType, int amount) {
        Calendar c = toCalendar(date);
        c.add(periodType, amount);
        return c.getTime();
    }

    /**
     * Округлити до дати (без часу)
     * @param date дата з часом
     * @return дата без часу
     */
    public static Date truncate(Date date) {
        Calendar c = toCalendar(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Отримати календар для дати
     * @param date дата, для якої необхідно отримати календар
     * @return календар для дати
     */
    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
}
