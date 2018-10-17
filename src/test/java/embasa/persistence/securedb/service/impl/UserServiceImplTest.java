package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.UserService;
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

import java.util.Date;
import java.util.List;

import static embasa.util.DateUtil.truncate;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class UserServiceImplTest {

    @Autowired
    @Qualifier("secureDBUserService")
    UserService userService;

    @Autowired
    @Qualifier(value = "secureDBTransactionManager")
    PlatformTransactionManager txManager;

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            User user = new User();
            user.setUsername("_test_user_login_");
            user.setName("user_name");
            user.setEmail("_test_user_login_@gmail.com");
            user.setEnabled(true);
            user.setComment("test_comment");
            user.setPassword("SECURE");
            user.setPasswordExpiration(new Date());

            userService.save(user);
            Long userId = user.getId();
            assertNotNull(userId);

            User dbUser = userService.findById(userId);
            assertNotNull(dbUser);
            assertEquals(user.getUsername(), dbUser.getUsername());
            assertEquals(user.getName(), dbUser.getName());
            assertEquals(user.getEmail(), dbUser.getEmail());
            assertEquals(user.getEnabled(), dbUser.getEnabled());
            assertEquals(user.getComment(), dbUser.getComment());
            assertEquals(user.getPassword(), dbUser.getPassword());
            assertEquals(truncate(user.getPasswordExpiration()), dbUser.getPasswordExpiration());
            assertEquals(user, dbUser);

            assertTrue(userService.isExist(userId));

            User loginUser = userService.findByLogin(user.getUsername());
            assertNotNull(loginUser);
            assertEquals(userId, loginUser.getId());

            User emailUser = userService.findByEmail(user.getEmail());
            assertNotNull(emailUser);
            assertEquals(userId, emailUser.getId());

            List<User> users = userService.findAll();
            assertFalse(users.isEmpty());
            assertTrue(users.contains(user));

            user.setUsername(user.getName() + "_1");
            user.setEmail(user.getEmail() + "_1");
            user.setEnabled(false);
            user.setComment(user.getComment() + "_1");
            user.setPassword(user.getPassword() + "_1");
            user.setPasswordExpiration(null);

            userService.update(user);
            dbUser = userService.findById(userId);
            assertEquals(user.getUsername(), dbUser.getUsername());
            assertEquals(user.getEmail(), dbUser.getEmail());
            assertEquals(user.getEnabled(), dbUser.getEnabled());
            assertEquals(user.getComment(), dbUser.getComment());
            assertEquals(user.getPassword(), dbUser.getPassword());
            assertNull(dbUser.getPasswordExpiration());
            assertEquals(user, dbUser);

            userService.delete(userId);
            assertFalse(userService.isExist(userId));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}