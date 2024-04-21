package org.xtext.demo.dsl.ls.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.websocket.WebSocketEndpoint;
import org.xtext.demo.dsl.ls.LanguageServerModule;

import java.io.PrintWriter;
import java.util.Collection;

public class LanguageServerEndpoint extends WebSocketEndpoint<LanguageClient> {
    static final Injector injector = Guice.createInjector(new LanguageServerModule());

    @Override
    protected void configure(Launcher.Builder<LanguageClient> builder) {
//    if (traceEnabled) {
//      PrintWriter printWriter = new PrintWriter(System.out);
//      builder.traceMessages(printWriter);
//    }
        builder.setLocalService(injector.getInstance(LanguageServer.class));
        builder.setRemoteInterface(LanguageClient.class);
    }

    @Override
    protected void connect(Collection<Object> localServices, LanguageClient remoteProxy) {
        localServices.stream().forEach(a -> {
            if (a instanceof LanguageClientAware) {
                try {
                    LanguageClientAware t = (LanguageClientAware) a;
                    t.connect(remoteProxy);
                } catch (Exception e) {
                    // TODO: LOG here properly
                }
            }
        });
    }
}
