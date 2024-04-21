package org.xtext.demo.dsl.ls.server;

import com.google.common.base.Objects;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;

public class WebSocketServerLauncher {
    public static final String HOST = "-host";
    public static final String DEFAULT_HOST = "0.0.0.0";
    private static final Logger logger = Logger.getLogger(WebSocketServerLauncher.class);
    private static final int port = 5008;

    public static void main(String[] args) {
        String host = getHost(args);
        logger.info(String.format("Starting language server on port {%d}", port));
        Server server = null;
        try {
            server = new Server(host, port, null, null, WebsocketServerConfigProvider.class, HealthCheckEndpoint.class);
            server.start();
            logger.info(String.format("Language Server started at {%s}:{%d}", host, port));
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.error("Error while starting the language server. Please try starting the server again.", e);
        } finally {
            if (server != null) {
                server.stop();
            }
            logger.warn("Shutting down language server.");
        }
    }

    protected static String getValue(String[] args, String argName) {
        for (int i = 0; (i < (args.length - 1)); i++) {
            if (Objects.equal(args[i], argName)) {
                return args[i + 1];
            }
        }
        return null;
    }

    protected static String getHost(String... args) {
        String host = getValue(args, HOST);
        if (host != null) {
            return host;
        } else {
            return DEFAULT_HOST;
        }
    }
}
