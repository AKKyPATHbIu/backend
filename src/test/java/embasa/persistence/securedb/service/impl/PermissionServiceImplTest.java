package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.service.impl.ServiceUtil;
import embasa.persistence.securedb.model.Permission;
import embasa.persistence.securedb.model.Role;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.PermissionService;
import embasa.persistence.securedb.service.RoleService;
import embasa.persistence.securedb.service.UserService;
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
public class PermissionServiceImplTest {

    @Autowired
    @Qualifier(value = "secureDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier("secureDBPermissionService")
    PermissionService permissionService;

    @Autowired
    @Qualifier(value = "secureDBMsgValueService")
    MsgValueService valueService;

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    LanguageHolder languageHolder;

    @Autowired
    @Qualifier("secureDBUserService")
    UserService userService;

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("secureDBRoleService")
    RoleService roleService;

    private static String NAME_CODE = "_permission_name_code_";
    private static String DESC_CODE = "_permission_descr_code_";

    private static String NAME_CODE_1 = "_permission_name_code_1";
    private static String DESC_CODE_1 = "_permission_descr_code_1";

    private static String NAME_CODE_2 = "_permission_name_code_2";
    private static String DESC_CODE_2 = "_permission_descr_code_2";

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {

            ServiceUtil.createLocalizeResources(NAME_CODE, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE, valueService);

            MsgValue msgNameUA = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_UA);
            MsgValue msgNameRU = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_RU);
            MsgValue msgNameEN = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_EN);

            assertNotNull(msgNameUA);
            assertNotNull(msgNameRU);
            assertNotNull(msgNameEN);

            MsgValue msgDescrUA = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_UA);
            MsgValue msgDescrRU = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_RU);
            MsgValue msgDescrEN = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_EN);

            assertNotNull(msgDescrUA);
            assertNotNull(msgDescrRU);
            assertNotNull(msgDescrEN);

            Permission p = new Permission();
            p.setModuleId(10);
            p.setNameCode(NAME_CODE);
            p.setDescCode(DESC_CODE);
            permissionService.save(p);
            Long id = p.getId();
            assertNotNull(id);
            assertTrue(permissionService.isExist(id));

            Permission dbPerm = permissionService.findById(id);
            assertNotNull(dbPerm);
            assertEquals(p.getModuleId(), dbPerm.getModuleId());
            assertEquals(p.getNameCode(), dbPerm.getNameCode());
            assertEquals(p.getDescCode(), dbPerm.getDescCode());

            assertEquals(msgNameUA.getValue(), dbPerm.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
            assertEquals(msgNameRU.getValue(), dbPerm.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
            assertEquals(msgNameEN.getValue(), dbPerm.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_EN)));

            assertEquals(msgDescrUA.getValue(), dbPerm.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
            assertEquals(msgDescrRU.getValue(), dbPerm.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
            assertEquals(msgDescrEN.getValue(), dbPerm.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_EN)));

            List<Permission> permissions = permissionService.findAll();
            assertFalse(permissions.isEmpty());
            assertTrue(permissions.contains(dbPerm));

            permissionService.delete(id);
            assertFalse(permissionService.isExist(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }

    @Test
    public void testFindByUser() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            ServiceUtil.createLocalizeResources(NAME_CODE, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE, valueService);
            ServiceUtil.createLocalizeResources(NAME_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(NAME_CODE_2, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE_2, valueService);

            Permission p = new Permission();
            p.setModuleId(10);
            p.setNameCode(NAME_CODE);
            p.setDescCode(DESC_CODE);
            permissionService.save(p);
            assertNotNull(p.getId());

            Permission p1 = new Permission();
            p1.setModuleId(10);
            p1.setNameCode(NAME_CODE_1);
            p1.setDescCode(DESC_CODE_1);
            permissionService.save(p1);
            assertNotNull(p1.getId());

            Permission p2 = new Permission();
            p2.setModuleId(10);
            p2.setNameCode(NAME_CODE_1);
            p2.setDescCode(DESC_CODE_1);
            permissionService.save(p2);
            assertNotNull(p2.getId());

            User u = new User();
            u.setUsername("_test_user_name_");
            u.setEmail("email@gmail.com");
            u.setPassword("S3CRET");
            u.setEnabled(true);
            userService.save(u);
            assertNotNull(u.getId());

            String TIE_SQL = "INSERT INTO user_permissions(user_id, permission_id) VALUES (?, ?)";
            jdbcTemplate.update(TIE_SQL, u.getId(), p.getId());
            jdbcTemplate.update(TIE_SQL, u.getId(), p1.getId());
            jdbcTemplate.update(TIE_SQL, u.getId(), p2.getId());

            List<Permission> permissions = permissionService.findByUser(u.getId());
            assertEquals(3, permissions.size());
            assertTrue(permissions.contains(p));
            assertTrue(permissions.contains(p1));
            assertTrue(permissions.contains(p2));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }

    @Test
    public void testFindByRole() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            ServiceUtil.createLocalizeResources(NAME_CODE, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE, valueService);
            ServiceUtil.createLocalizeResources(NAME_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE_1, valueService);

            Permission p1 = new Permission();
            p1.setModuleId(10);
            p1.setNameCode(NAME_CODE);
            p1.setDescCode(DESC_CODE);
            permissionService.save(p1);
            assertNotNull(p1.getId());

            Permission p2 = new Permission();
            p2.setModuleId(10);
            p2.setNameCode(NAME_CODE_1);
            p2.setDescCode(DESC_CODE_1);
            permissionService.save(p2);
            assertNotNull(p2.getId());

            Role role = new Role();
            role.setName("_test_role_name_");
            role.setDescription("_test_role_descr_");
            roleService.save(role);
            assertNotNull(role.getId());

            String BIND_PERMISSION_TO_ROLE = "INSERT INTO role_permissions(role_id, permission_id) VALUES(?, ?)";
            jdbcTemplate.update(BIND_PERMISSION_TO_ROLE, role.getId(), p1.getId());
            jdbcTemplate.update(BIND_PERMISSION_TO_ROLE, role.getId(), p2.getId());

            List<Permission> permissions = permissionService.findByRole(role.getId());
            assertEquals(2, permissions.size());
            assertTrue(permissions.contains(p1));
            assertTrue(permissions.contains(p2));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}