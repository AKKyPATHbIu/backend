package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
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
public class RoleServiceImplTest {

    @Autowired
    @Qualifier(value = "secureDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier("secureDBRoleService")
    RoleService roleService;

    @Autowired
    @Qualifier("secureDBUserService")
    UserService userService;

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("secureDBPermissionService")
    PermissionService permissionService;

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            Role role = new Role();
            role.setDescription("_test_role_descr_");
            role.setName("_test_role_name_");
            roleService.save(role);
            Long roleId = role.getId();
            assertNotNull(roleId);

            assertTrue(roleService.isExist(roleId));
            Role dbRole = roleService.findById(roleId);
            assertEquals(role.getDescription(), dbRole.getDescription());
            assertEquals(role.getName(), dbRole.getName());
            assertEquals(role, dbRole);

            List<Role> roles = roleService.findAll();
            assertFalse(roles.isEmpty());
            assertTrue(roles.contains(dbRole));

            roleService.delete(roleId);
            assertFalse(roleService.isExist(roleId));
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
            Role role1 = new Role();
            role1.setName("_test_role_name_1");
            role1.setDescription("_test_role_descr_1");
            roleService.save(role1);
            assertNotNull(role1.getId());

            Role role2 = new Role();
            role2.setName("_test_role_name_2");
            role2.setDescription("_test_role_descr_2");
            roleService.save(role2);
            assertNotNull(role2.getId());

            Role role3 = new Role();
            role3.setName("_test_role_name_3");
            role3.setDescription("_test_role_descr_4");
            roleService.save(role3);
            assertNotNull(role3.getId());

            User u = new User();
            u.setUsername("_test_user_name_");
            u.setEnabled(true);
            u.setEmail("user@gmail.com");
            userService.save(u);
            assertNotNull(u.getId());

            String BIND_USER_WITH_ROLE = "INSERT INTO user_roles(user_id, role_id) VALUES(?, ?)";
            jdbcTemplate.update(BIND_USER_WITH_ROLE, u.getId(), role1.getId());
            jdbcTemplate.update(BIND_USER_WITH_ROLE, u.getId(), role2.getId());
            jdbcTemplate.update(BIND_USER_WITH_ROLE, u.getId(), role3.getId());

            List<Role> roles = roleService.findByUser(u.getId());
            assertEquals(3, roles.size());
            assertTrue(roles.contains(role1));
            assertTrue(roles.contains(role2));
            assertTrue(roles.contains(role3));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }

    @Test
    public void testFindByPermission() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            String nameCode1 = "_test_name_code_1";
            String descrCode1 = "_test_descr_code_1";

            Permission p = new Permission();
            p.setNameCode(nameCode1);
            p.setDescCode(descrCode1);
            permissionService.save(p);
            assertNotNull(p.getId());

            Role r1 = new Role();
            r1.setName("_test_role_name_1");
            r1.setDescription("_test_role_descr_1");
            roleService.save(r1);
            assertNotNull(r1.getId());

            Role r2 = new Role();
            r2.setName("_test_role_name_2");
            r2.setDescription("_test_role_descr_2");
            roleService.save(r2);
            assertNotNull(r2.getId());

            Role r3 = new Role();
            r3.setName("_test_role_name_3");
            r3.setDescription("_test_role_descr_3");
            roleService.save(r3);
            assertNotNull(r3.getId());

            String BIND_PERMISSION_TO_ROLE = "INSERT INTO role_permissions(role_id, permission_id) VALUES(?, ?)";
            jdbcTemplate.update(BIND_PERMISSION_TO_ROLE, r1.getId(), p.getId());
            jdbcTemplate.update(BIND_PERMISSION_TO_ROLE, r2.getId(), p.getId());
            jdbcTemplate.update(BIND_PERMISSION_TO_ROLE, r3.getId(), p.getId());

            List<Role> roles = roleService.findByPermission(p.getId());
            assertEquals(3, roles.size());
            assertTrue(roles.contains(r1));
            assertTrue(roles.contains(r2));
            assertTrue(roles.contains(r3));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }

    @Test
    public void testFindByParentRole() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            Role parentRole = new Role();
            parentRole.setName("_test_parent_role_name_");
            parentRole.setDescription("_test_prent_role_descr_");
            roleService.save(parentRole);
            assertNotNull(parentRole.getId());

            Role r1 = new Role();
            r1.setName("_test_role_name_1");
            r1.setDescription("_test_role_descr_1");
            roleService.save(r1);
            assertNotNull(r1.getId());

            Role r2 = new Role();
            r2.setName("_test_role_name_2");
            r2.setDescription("_test_role_descr_2");
            roleService.save(r2);
            assertNotNull(r2.getId());

            Role r3 = new Role();
            r3.setName("_test_role_name_3");
            r3.setDescription("_test_role_descr_3");
            roleService.save(r3);
            assertNotNull(r3.getId());

            String BIND_ROLE_TO_ROLE = "INSERT INTO role_roles(parent_role_id, child_role_id) VALUES(?, ?)";
            jdbcTemplate.update(BIND_ROLE_TO_ROLE, parentRole.getId(), r1.getId());
            jdbcTemplate.update(BIND_ROLE_TO_ROLE, parentRole.getId(), r2.getId());
            jdbcTemplate.update(BIND_ROLE_TO_ROLE, parentRole.getId(), r3.getId());

            List<Role> roles = roleService.findByParentRole(parentRole.getId());
            assertEquals(3, roles.size());
            assertTrue(roles.contains(r1));
            assertTrue(roles.contains(r2));
            assertTrue(roles.contains(r3));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}