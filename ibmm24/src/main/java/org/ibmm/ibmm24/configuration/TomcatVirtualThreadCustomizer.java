package org.ibmm.ibmm24.configuration;

import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Component
public class TomcatVirtualThreadCustomizer
        implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addProtocolHandlerCustomizers(
                (TomcatProtocolHandlerCustomizer<ProtocolHandler>) protocolHandler -> {
                    ThreadFactory virtualThreadFactory = Thread.ofVirtual()
                            .name("virtual-tomcat-thread-", 0)
                            .factory();
                    Executor virtualThreadExecutor = Executors.newThreadPerTaskExecutor(virtualThreadFactory);
                    protocolHandler.setExecutor(command -> virtualThreadExecutor.execute(command));
                });
    }
}
