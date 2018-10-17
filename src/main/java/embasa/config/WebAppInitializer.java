package embasa.config;

import embasa.i18n.LocaleUtil;
import embasa.listeners.SessionListener;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class , WebSecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new SessionListener());
        LocaleContextHolder.setLocale(LocaleUtil.LOCALE_UA);
        servletContext.setInitParameter("spring.profiles.active", "PRODUCTION");
        servletContext.addListener(new ServletContextListener() {
            public void contextInitialized(ServletContextEvent servletContextEvent) {

            }

            public void contextDestroyed(ServletContextEvent servletContextEvent) {
                Logger.getRootLogger().removeAllAppenders();
                Logger.getRootLogger().addAppender(new NullAppender());
            }
        });
    }
}
