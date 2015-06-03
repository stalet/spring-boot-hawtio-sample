package org.tomten.samples.springboot;

import io.hawt.HawtioContextListener;
import io.hawt.config.ConfigFacade;
import io.hawt.springboot.HawtPlugin;
import io.hawt.springboot.PluginService;
import io.hawt.system.ConfigManager;
import io.hawt.web.AuthenticationFilter;
import io.hawt.web.CORSFilter;
import io.hawt.web.CacheHeadersFilter;
import io.hawt.web.ContextFormatterServlet;
import io.hawt.web.ExportContextServlet;
import io.hawt.web.GitServlet;
import io.hawt.web.JavaDocServlet;
import io.hawt.web.LoginServlet;
import io.hawt.web.LogoutServlet;
import io.hawt.web.PodServlet;
import io.hawt.web.ProxyServlet;
import io.hawt.web.RedirectFilter;
import io.hawt.web.ServiceServlet;
import io.hawt.web.SessionExpiryFilter;
import io.hawt.web.UploadServlet;
import io.hawt.web.UserServlet;
import io.hawt.web.XFrameOptionsFilter;
import io.hawt.web.keycloak.KeycloakServlet;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;


@Configuration
public class HawtioConfiguration {

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
        System.setProperty(AuthenticationFilter.HAWTIO_AUTHENTICATION_ENABLED, "false");
        final ConfigManager configManager = new ConfigManager();
        configManager.init();
        servletContext.setAttribute("ConfigManager", configManager);
    }

    @Bean
    public HawtPlugin samplePlugin() {
        return new HawtPlugin("sample-plugin", "/hawtio/plugins", "", new String[] { "sample-plugin/js/sample-plugin.js" });
    }

    @Bean(initMethod = "init")
    public ConfigFacade configFacade() throws Exception {
        return new ConfigFacade();
    }

    @Bean
    public PluginService pluginService() {
        return new PluginService();
    }

    @Bean
    public ServletRegistrationBean userServlet() {
        return new ServletRegistrationBean(new UserServlet(), "/user/*", "/hawtio/user/*");
    }

    @Bean
    public ServletRegistrationBean jolokiaproxy() {
        return new ServletRegistrationBean(new ProxyServlet(), "/hawtio/proxy/*");
    }

    @Bean
    public ServletRegistrationBean kubeservice() {
        return new ServletRegistrationBean(new ServiceServlet(), "/hawtio/service/*");
    }

    @Bean
    public ServletRegistrationBean kubepod() {
        return new ServletRegistrationBean(new PodServlet(), "/hawtio/pod/*");
    }

    @Bean
    public ServletRegistrationBean fileupload() {
        return new ServletRegistrationBean(new UploadServlet(), "/hawtio/file-upload/*");
    }

    @Bean
    public ServletRegistrationBean loginservlet() {
        return new ServletRegistrationBean(new LoginServlet(), "/hawtio/auth/login/*");
    }

    @Bean
    public ServletRegistrationBean logoutservlet() {
        return new ServletRegistrationBean(new LogoutServlet(), "/hawtio/auth/logout/*");
    }

    @Bean
    public ServletRegistrationBean keycloakservlet() {
        return new ServletRegistrationBean(new KeycloakServlet(), "/hawtio/keycloak/*");
    }

    @Bean
    public ServletRegistrationBean exportcontextservlet() {
        return new ServletRegistrationBean(new ExportContextServlet(), "/hawtio/exportContext/*");
    }

    @Bean
    public ServletRegistrationBean mavenSource() {
        return new ServletRegistrationBean(new JavaDocServlet(), "/hawtio/javadoc/*");
    }

    @Bean
    public ServletRegistrationBean contextFormatter() {
        return new ServletRegistrationBean(new ContextFormatterServlet(), "/hawtio/contextFormatter/*");
    }

    @Bean
    public ServletRegistrationBean gitServlet() {
        return new ServletRegistrationBean(new GitServlet(), "/hawtio/git/*");
    }

    @Bean
    public ServletListenerRegistrationBean hawtioContextListener() {
        return new ServletListenerRegistrationBean<>(new HawtioContextListener());
    }

    @Bean
    public ServletListenerRegistrationBean fileCleanerCleanup() {
        return new ServletListenerRegistrationBean<>(new FileCleanerCleanup());
    }

    @Bean
    public FilterRegistrationBean redirectFilter() {
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new RedirectFilter());
        filter.setUrlPatterns(Collections.singletonList("/hawtio/*"));
        return filter;
    }

    @Bean
    public FilterRegistrationBean sessionExpiryFilter() {
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new SessionExpiryFilter());
        filter.setUrlPatterns(Collections.singletonList("/hawtio/*"));
        return filter;
    }

    @Bean
    public FilterRegistrationBean cacheFilter() {
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new CacheHeadersFilter());
        filter.setUrlPatterns(Collections.singletonList("/hawtio/*"));
        return filter;
    }

    @Bean
    public FilterRegistrationBean CORSFilter() {
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new CORSFilter());
        filter.setUrlPatterns(Collections.singletonList("/hawtio/*"));
        return filter;
    }

    @Bean
    public FilterRegistrationBean XFrameOptionsFilter() {
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new XFrameOptionsFilter());
        filter.setUrlPatterns(Collections.singletonList("/hawtio/*"));
        return filter;
    }

    @Bean
    public FilterRegistrationBean AuthenticationFilter() {
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new AuthenticationFilter());
        filter.setUrlPatterns(Arrays.asList("/auth/*", "/jolokia/*", "/upload/*", "/javadoc/*"));
        return filter;
    }
}
