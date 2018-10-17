package embasa.versioning;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionImplTest {

    @Test
    public void after() {
        Version v1 = new VersionImpl(1,2,3);
        Version v2 = new VersionImpl(10,20,30);
        Version v3 = new VersionImpl(1,3,0);
        Version v4 = new VersionImpl(1,2,4);
        Version v5 = new VersionImpl(2,2,3);
        assertTrue(v2.after(v1));
        assertTrue(v3.after(v1));
        assertTrue(v4.after(v1));
        assertTrue(v5.after(v1));

        Version v6 = new VersionImpl(1,2,3);
        assertFalse(v6.after(v1));
    }

    @Test
    public void before() {
        Version v1 = new VersionImpl(1,2,3);
        Version v2 = new VersionImpl(10,20,30);
        Version v3 = new VersionImpl(1,3,0);
        Version v4 = new VersionImpl(1,2,4);
        Version v5 = new VersionImpl(2,2,3);
        assertTrue(v1.before(v2));
        assertTrue(v1.before(v3));
        assertTrue(v1.before(v4));
        assertTrue(v1.before(v5));
        Version v6 = new VersionImpl(1,2,3);
        assertFalse(v1.before(v6));
    }

    @Test
    public void equals() {
        Version v1 = new VersionImpl(10,20,30);
        Version v2 = new VersionImpl(10,20,30);
        assertTrue(v1.equals(v2));
        Version v3 = new VersionImpl(11,20,30);
        Version v4 = new VersionImpl(10,21,30);
        Version v5 = new VersionImpl(10,20,33);
        assertFalse(v1.equals(v3));
        assertFalse(v1.equals(v4));
        assertFalse(v1.equals(v5));
    }

    @Test
    public void getMajorVersion() {
        Version version = new VersionImpl(10,20,30);
        assertEquals(10, version.getMajorVersion());
    }

    @Test
    public void getMinorVersion() {
        Version version = new VersionImpl(10,200,30);
        assertEquals(200, version.getMinorVersion());
    }

    @Test
    public void getPatchVersion() {
        Version version = new VersionImpl(10,200,3);
        assertEquals(3, version.getPatchVersion());
    }

    @Test
    public void testToString() {
        Version version = new VersionImpl(10,200,3);
        assertEquals("10.200.3", version.toString());
        version = new VersionImpl(0,1,2);
        assertEquals("0.1.2", version.toString());
    }
}