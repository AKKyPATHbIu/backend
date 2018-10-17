package embasa.persistence.maindb.model;

import org.junit.Test;
import org.springframework.messaging.core.CachingDestinationResolverProxy;

import java.util.Objects;

import static org.junit.Assert.*;

public class CardEntityTest {

    private final Long id = 333L;
    private final Long moduleId = 10L;
    private final Long clinicId = 20L;
    private final String name = "name";
    private final String descr = "descr";
    private final Long status = 30L;
    private final String tableName = "tablename";
    private final String viewName = "viewname";
    private final String uiConf = "{\"json\" : true}";

    @Test
    public void testHashCode() {
        CardEntity c = new CardEntity();
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setModuleId(moduleId);
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setClinicId(clinicId);
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setName(name);
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setDescr(descr);
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setTableName(tableName);
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setViewName(viewName);
        assertEquals(Objects.hash(c.getModuleId(), c.getClinicId(), c.getName(), c.getDescr(), c.getTableName(), c.getViewName()), c.hashCode());
        c.setId(id);
        assertEquals(id.hashCode(), c.hashCode());
    }

    @Test
    public void testEquals() {
        CardEntity c = new CardEntity();
        assertEquals(c, c);
        assertNotEquals(c, new Object());

    }

    @Test
    public void test() {
        CardEntity c = new CardEntity();
        c.setId(id);
        c.setModuleId(moduleId);
        c.setClinicId(clinicId);
        c.setName(name);
        c.setDescr(descr);
        c.setStatus(status);
        c.setTableName(tableName);
        c.setViewName(viewName);
        c.setUiConf(uiConf);

        assertEquals(id, c.getId());
        assertEquals(moduleId, c.getModuleId());
        assertEquals(clinicId, c.getClinicId());
        assertEquals(name, c.getName());
        assertEquals(descr, c.getDescr());
        assertEquals(status, c.getStatus());
        assertEquals(tableName, c.getTableName());
        assertEquals(viewName, c.getViewName());
        assertEquals(uiConf, c.getUiConf());

        assertFalse(c.isSystem());
        c.setSystem(true);
        assertTrue(c.isSystem());
    }
}