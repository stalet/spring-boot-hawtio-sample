package org.tomten.samples.springboot;

import io.hawt.config.ConfigFacade;
import io.hawt.springboot.HawtPlugin;
import io.hawt.springboot.PluginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import(HawtioConfiguration.class)
public class Application {

    /**
     * Loading an example plugin
     * @return
     */
    @Bean
    public HawtPlugin samplePlugin() {
        return new HawtPlugin("sample-plugin", "/hawtio/plugins", "", new String[] { "sample-plugin/js/sample-plugin.js" });
    }

    /**
     * Set things up to be in offline mode
     * @return
     * @throws Exception
     */
    @Bean
    public ConfigFacade configFacade() throws Exception {
        ConfigFacade config = new ConfigFacade() {
            public boolean isOffline() {
                return true;
            }
        };
        config.init();
        return config;
    }

    /**
     * Register rest endpoint to handle requests for /plugin, and return all registered plugins.
     * @return
     */
    @Bean
    public PluginService pluginService(){
        return new PluginService();
    }

    /**
     * Application main.
     *
     * @param args application arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}