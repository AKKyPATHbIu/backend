package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.service.impl.ServiceUtil;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.model.UserEcp;
import embasa.persistence.securedb.service.AcskService;
import embasa.persistence.securedb.service.UserEcpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class UserEcpServiceImplTest {

    @Autowired
    @Qualifier("secureDBAcskService")
    AcskService acskService;

    @Autowired
    @Qualifier(value = "secureDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier("secureDBMsgValueService")
    MsgValueService msgValueService;

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("secureDBUserEcpService")
    UserEcpService userEcpService;

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    LanguageHolder languageHolder;

    static final String ACSK_RECOURCE_CODE = "_test_acsk_code_";

    private final byte[] KEY_DATA_1 = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private final byte[] KEY_DATA_2 = new byte[] {11, 21, 31, 41, 51, 61, 71, 81, 91, 1};

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            User user = new User();
            user.setEnabled(true);
            user.setUsername("_test_user_");

            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users")
                    .withSchemaName(DataBase.SECURE_DB.getSchema());
            jdbcInsert.setGeneratedKeyName("id");

            Map<String, Object> params = new HashMap<>();
            params.put("login", user.getUsername());
            params.put("name", user.getName());
            params.put("account_enabled", true);
            Long userId = jdbcInsert.executeAndReturnKey(params).longValue();
            user.setId(userId);
            assertNotNull(user.getId());

            ServiceUtil.createLocalizeResources(ACSK_RECOURCE_CODE, msgValueService);
            Acsk acsk = new Acsk();
            acsk.setCmpAddress("address");
            acsk.setCmpPort(10);
            acsk.setNameCode(ACSK_RECOURCE_CODE);

            acskService.save(acsk);
            assertNotNull(acsk.getId());

            UserEcp userEcp = new UserEcp();
            userEcp.setAcsk(acsk);
            userEcp.setUser(user);
            userEcp.setValue(KEY_DATA_1);

            userEcpService.save(userEcp);
            Long ecpId = userEcp.getId();
            assertNotNull(ecpId);
            UserEcp dbEcp = userEcpService.findByEdsKey(KEY_DATA_1);
            assertNotNull(dbEcp);
            assertEquals(ecpId, dbEcp.getId());
            assertTrue(userEcpService.isExist(ecpId));
            assertTrue(userEcpService.findAll().contains(dbEcp));

            List<UserEcp> ecps = userEcpService.findByUser(userId);

            assertEquals(1, ecps.size());
            assertTrue(ecps.contains(dbEcp));

            UserEcp userEcp2 = new UserEcp();
            userEcp2.setAcsk(acsk);
            userEcp2.setUser(user);
            userEcp2.setValue(KEY_DATA_2);
            userEcpService.save(userEcp2);
            assertNotNull(userEcp2.getId());

            ecps = userEcpService.findByUser(userId);
            assertEquals(2, ecps.size());
            assertTrue(ecps.contains(dbEcp));
            assertTrue(ecps.contains(userEcp2));
        } finally {
            txManager.rollback(txStatus);
        }
    }
}