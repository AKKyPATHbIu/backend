package embasa.frontinteraction.command;

import embasa.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class CommandHolderEmbasImplTest {

    @Autowired
    @Qualifier(value = "commandHolderEmbas")
    CommandHolder cmdHolder;

    @Test
    public void testGetAvailableCommands() {
        assertNotNull(cmdHolder);
        Command[] commands = cmdHolder.getAvailableCommands();
        assertTrue(commands.length > 0);
    }
}