package embasa.persistence.securedb.model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class GroupTest {

    @Test
    public void testHashCode() {
        Group group = new Group();
        assertEquals(Objects.hash(group.getName(), group.getDescription()), group.hashCode());
        group.setName("name");
        assertEquals(Objects.hash(group.getName(), group.getDescription()), group.hashCode());
        group.setDescription("descr");
        assertEquals(Objects.hash(group.getName(), group.getDescription()), group.hashCode());
        group.setId(1L);
        assertEquals(group.getId().hashCode(), group.hashCode());
    }

    @Test
    public void testEquals() {
        Group group1 = new Group();
        assertEquals(group1, group1);

        Group group2 = new Group();
        assertNotEquals(group1, group2);

        group1.setName("name");
        assertNotEquals(group1, group2);
        group2.setName(group1.getName());
        assertEquals(group1, group2);

        group1.setDescription("decsr");
        assertNotEquals(group1, group2);
        group2.setDescription(group1.getDescription());
        assertEquals(group1, group2);

        group1.setId(2000L);
        assertNotEquals(group1, group2);
        group2.setId(group1.getId());
        assertEquals(group1, group2);
    }
}