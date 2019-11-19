package com.dani.vbank.spring;

import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ContainerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setTomcatContextCustomizers(Arrays.asList(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                context.setUseHttpOnly(false);
            }
        }));
    }
}