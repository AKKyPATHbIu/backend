package embasa.crypto;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CertificateInfoTest {

    Calendar calendar = new GregorianCalendar();

    @Test
    public void isExpired() {
        CertificateInfo certInfo = new CertificateInfo();

        calendar.set(2001, 01, 01);
        Date expiredBegin = calendar.getTime();
        calendar.set(2002, 01, 01);
        Date expiredEnd = calendar.getTime();
        certInfo.setPkBeginTime(expiredBegin);
        certInfo.setPkEndTime(expiredEnd);
        assertTrue(certInfo.isExpired());

        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -1);
        Date workedBegin = calendar.getTime();
        calendar.add(Calendar.MONTH, 2);
        Date workedEnd = calendar.getTime();
        certInfo.setPkBeginTime(workedBegin);
        certInfo.setPkEndTime(workedEnd);
        assertFalse(certInfo.isExpired());
    }
}