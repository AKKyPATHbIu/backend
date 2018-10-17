package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.securedb.model.Group;
import embasa.persistence.securedb.service.GroupService;
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
public class GroupServiceImplTest {

    @Autowired
    @Qualifier(value = "secureDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier("secureDBGroupService")
    GroupService groupService;

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            Group g = new Group();
            g.setDescription("_test_group_description_");
            g.setName("_test_group_name_");
            groupService.save(g);

            Long id = g.getId();
            assertNotNull(id);
            assertTrue(groupService.isExist(g.getId()));

            Group dbGroup = groupService.findById(id);
            assertNotNull(dbGroup);
            assertEquals(g.getName(), dbGroup.getName());
            assertEquals(g.getDescription(), dbGroup.getDescription());

            List<Group> groups = groupService.findAll();
            assertFalse(groups.isEmpty());
            assertTrue(groups.contains(dbGroup));

            groupService.delete(id);
            assertFalse(groupService.isExist(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}