package embasa.persistence.maindb.service.impl;

import embasa.LangUtil;
import embasa.config.RootConfig;
import embasa.persistence.common.service.LanguageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class LanguageServiceImplTest {

    @Autowired
    @Qualifier("mainDBLanguageService")
    LanguageService langService;

    @Test
    public void findById() {
        LangUtil.testFindById(langService);
    }
}