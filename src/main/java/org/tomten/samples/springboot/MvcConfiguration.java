/*
 * Copyright (c) 2010-2015 Safetel AS, Norway
 */

package org.tomten.samples.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/hawtio/plugins/**").addResourceLocations("/app/", "classpath:/app/");
        registry.addResourceHandler("/hawtio/**").addResourceLocations("/app/", "classpath:/app/");
        registry.addResourceHandler("/hawtio/**").addResourceLocations("/", "classpath:/");
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/hawtio/index.html");
        registry.addViewController("/hawtio/plugin").setViewName("forward:/plugin");
        registry.addViewController("/hawtio/").setViewName("redirect:/hawtio/index.html");
    }

}
