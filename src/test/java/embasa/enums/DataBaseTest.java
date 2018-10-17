package embasa.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataBaseTest {

    @Test
    public void getDbName() {
        assertEquals("maindb", DataBase.MAIN_DB.getDbName());
        assertEquals("embas", DataBase.MAIN_DB.getSchema());
        assertEquals("securedb", DataBase.SECURE_DB.getDbName());
        assertEquals("secembas", DataBase.SECURE_DB.getSchema());
    }
}