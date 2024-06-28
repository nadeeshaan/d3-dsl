package org.nadeeshaan.d3dsl.ls.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.nadeeshaan.d3dsl.ls.LanguageServerModule;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class D3DslLangServerWSHandler extends TextWebSocketHandler {

    static final Injector injector = Guice.createInjector(new LanguageServerModule());
    private final LanguageServer languageServer;
    private final WebSocketMessageHandler messageJsonHandler;

    public D3DslLangServerWSHandler() {
        this.languageServer = injector.getInstance(LanguageServer.class);
        this.messageJsonHandler = new WebSocketMessageHandler();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        WebSocketLauncherBuilder launcherBuilder = new WebSocketLauncherBuilder();
        launcherBuilder
                .setSession(session)
                .setMessageHandler(this.messageJsonHandler)
                .setLocalService(this.languageServer)
                .setRemoteInterface(LanguageClient.class);
        Launcher<LanguageClient> launcher = launcherBuilder.create();

        if (this.languageServer instanceof LanguageClientAware) {
            ((LanguageClientAware) this.languageServer).connect(launcher.getRemoteProxy());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        this.messageJsonHandler.onMessage(message.getPayload());
    }
}
