package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.maindb.model.CardEntity;
import embasa.persistence.maindb.model.CardEntityAttr;
import embasa.persistence.maindb.service.CardEntityAttrService;
import embasa.persistence.maindb.service.CardEntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class CardEntityAttrServiceImplTest {

    @Autowired
    CardEntityService cardEntityService;

    @Autowired
    CardEntityAttrService cardEntityAttrService;

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Test
    public void test() {
        CardEntity p = new CardEntity();
        p.setId(0L);
        p.setModuleId(4L);
        p.setClinicId(null);
        p.setSystem(true);
        p.setName("attr_test");
        p.setStatus(0L);

        Long typeId = jdbcTemplate.queryForObject("SELECT id FROM entity_data_types LIMIT 1", Long.class);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            cardEntityService.save(p);

            CardEntityAttr attr = new CardEntityAttr();
            attr.setConstrEntityId(p.getId());
            attr.setName("attr_name");
            attr.setFieldName("attr_field_name");
            attr.setDescr("attr_descr");
            attr.setType(typeId);
            attr.setPosition(10L);
            attr.setStatus(0L);

            cardEntityAttrService.save(attr);
            assertNotNull(attr.getId());
            assertTrue(cardEntityAttrService.isExist(attr.getId()));
            assertFalse(cardEntityAttrService.findAll().isEmpty());
            assertNotNull(cardEntityAttrService.findById(attr.getId()));

            CardEntityAttr attr1 = cardEntityAttrService.findById(attr.getId());
            assertNotNull(attr1);

            assertEquals(attr.getName(), attr1.getName());
            assertEquals(attr.getFieldName(), attr1.getFieldName());
            assertEquals(attr.getDescr(), attr1.getDescr());
            assertEquals(attr.getType(), attr1.getType());
            assertEquals(attr.getPosition(), attr1.getPosition());
            assertEquals(attr.getIndexed(), attr1.getIndexed());
            assertEquals(attr.getAudited(), attr1.getAudited());
            assertEquals(attr.getStatus(), attr1.getStatus());
            assertEquals(attr.getNullable(), attr1.getNullable());
            assertEquals(attr.getUiConf(), attr1.getUiConf());

            CardEntityAttr attr2 = new CardEntityAttr();
            attr2.setConstrEntityId(p.getId());
            attr2.setName("attr_name_2");
            attr2.setFieldName("attr_field_name_2");
            attr2.setDescr("attr_descr_2");
            attr2.setType(typeId);
            attr2.setPosition(1L);

            cardEntityAttrService.save(attr2);

            List<CardEntityAttr> attrs =  cardEntityAttrService.getByCardEntity(p.getId());
            assertTrue(attrs.size() == 2);
            assertTrue(attrs.contains(attr));
            assertTrue(attrs.contains(attr2));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(status);
        }
    }
}