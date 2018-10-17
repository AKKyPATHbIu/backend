package embasa.util;

import embasa.enums.DBDialect;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBDialectTest {

    @Test
    public void testData() {
        assertEquals("org.hibernate.dialect.PostgreSQLDialect", DBDialect.POSTGRESQL.getDialect());
        assertEquals("org.postgresql.Driver", DBDialect.POSTGRESQL.getDriver());
        assertEquals("jdbc:postgresql://", DBDialect.POSTGRESQL.getUrlPrefix());

        assertEquals("org.hibernate.dialect.MySQLDialect", DBDialect.MYSQL.getDialect());
        assertEquals("com.mysql.jdbc.Driver", DBDialect.MYSQL.getDriver());
        assertEquals("jdbc:mysql://", DBDialect.MYSQL.getUrlPrefix());
    }

    @Test
    public void testGetObjectFrom() {
        DBDialect postgres = DBDialect.POSTGRESQL;

        assertEquals(postgres, DBDialect.getObjectBy("postgresql"));
        assertEquals(postgres, DBDialect.getObjectBy("POSTgresql"));
        assertEquals(postgres, DBDialect.getObjectBy("POSTGRESQL"));
        assertEquals(postgres, DBDialect.getObjectBy("pOSTGRESQL"));
        assertEquals(postgres, DBDialect.getObjectBy("postgresqL"));

        DBDialect oracle = DBDialect.MYSQL;

        assertEquals(oracle, DBDialect.getObjectBy("mysql"));
        assertEquals(oracle, DBDialect.getObjectBy("Mysql"));
        assertEquals(oracle, DBDialect.getObjectBy("MYSQL"));
        assertEquals(oracle, DBDialect.getObjectBy("mySQL"));
        assertEquals(oracle, DBDialect.getObjectBy("mysqL"));
    }
}