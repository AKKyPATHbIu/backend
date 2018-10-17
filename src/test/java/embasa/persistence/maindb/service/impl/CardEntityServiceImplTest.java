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
public class CardEntityServiceImplTest {
    @Autowired
    CardEntityService cardEntityService;

    @Autowired
    CardEntityAttrService cardEntityAttrService;

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Test
    public void save() {
        CardEntity p = new CardEntity();
        p.setModuleId(4L);
        p.setClinicId(null);
        p.setSystem(false);
        p.setName("test");
        p.setUiConf("{ \"zzz\" : 2 }");

        CardEntityAttr attr = new CardEntityAttr();
        attr.setName("some_attr");
        attr.setType(2L);
        attr.setDescr("descr");
        attr.setAudited(false);
        attr.setPosition(10L);
        attr.setFieldName("field name");
        attr.setStatus(100L);

        p.getAttr().add(attr);

        attr = new CardEntityAttr();
        attr.setName("some_attr_1");
        attr.setType(1L);
        attr.setDescr("descr_2");
        attr.setAudited(false);
        attr.setPosition(2L);
        attr.setFieldName("field name_3");
        attr.setStatus(50L);
        p.getAttr().add(attr);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            cardEntityService.save(p);
            Long id = p.getId();
            assertNotNull(id);
            CardEntity ent = cardEntityService.findById(id);
            assertNotNull(ent);

            List<CardEntityAttr> attrs = cardEntityAttrService.getByCardEntity(p.getId());
            ent.setAttr(attrs);
            assertEquals(2, ent.getAttr().size());
            assertTrue(cardEntityService.isExist(id));
            List<CardEntity> ents = cardEntityService.findAll();
            assertTrue(ents.contains(ent));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            Long id = p.getId();
            if (id != null) {
                cardEntityService.delete(id);
                assertFalse(cardEntityService.isExist(id));
            }
            txManager.rollback(status);
        }
    }
}