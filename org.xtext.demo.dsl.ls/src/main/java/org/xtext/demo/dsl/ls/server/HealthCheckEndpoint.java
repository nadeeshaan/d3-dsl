package org.xtext.demo.dsl.ls.server;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/actuator/health")
public class HealthCheckEndpoint {

  private static final String HEALTH_RESPONSE = "{\n" + "    \"status\": \"UP\"\n" + "}";

  @OnMessage
  public String onMessage(String message, Session session) {
    return HEALTH_RESPONSE;
  }

  @OnOpen
  public void onOpen(Session session) throws IOException {
    session.getBasicRemote().sendText(HEALTH_RESPONSE);
  }
}
