package embasa.persistence.securedb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class DBVersionTest {

    @Test
    public void testHashCode() {
        DBVersion version = new DBVersion();
        version.setVersion("version");
        version.setDescription("descr");
        assertEquals(Objects.hash(version.getVersion()), version.hashCode());
    }

    @Test
    public void testEquals() {
        DBVersion version1 = new DBVersion();
        version1.setVersion("version1");

        DBVersion version2 = new DBVersion();
        version2.setVersion("version2");
        assertNotEquals(version1, version2);

        version2.setVersion(version1.getVersion());
        assertEquals(version1, version2);

        version1.setVersion(null);
        assertNotEquals(version1, version2);
        version2.setVersion(null);
        assertNotEquals(version1, version2);
    }
}