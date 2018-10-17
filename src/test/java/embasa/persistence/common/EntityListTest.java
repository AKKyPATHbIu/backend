package embasa.persistence.common;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.model.FieldsListable;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityListTest {

    @Test
    public void listFieldsValues() {
        EntityList<TestEntity> list = new EntityList<>();
        list.add(new TestEntity(1L, "test"));
        list.add(new TestEntity(null, null));
        list.add(new TestEntity(200L, null));

        assertEquals("ARRAY['(1,test)','(,)','(200,)']", list.listFieldsValues());
        list.clear();
        assertEquals("ARRAY[]", list.listFieldsValues());
        list.add(new TestEntity(10L, "text"));
        assertEquals("ARRAY['(10,text)']", list.listFieldsValues());

    }

    private class TestEntity implements FieldsListable {

        private Long id;
        private String name;

        public TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String listFieldsValues() {
            return "(" + JdbcUtil.objToStr(id) + "," + JdbcUtil.objToStr(name) + ")";
        }
    }
}