/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.controller.websocket;

import java.io.IOException;
import java.util.logging.Level;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;

/**
 *
 * @author Toms
 */
@ServerEndpoint("/hello")
public class TestWebsocketEndpoint {
    
    @OnMessage
    public String hello(String message, Session session) {
        Logger.getRootLogger().info("Received : "+ message);
        
        String broadcastMessage = "Server says: "+message;
        
        for(Session s : session.getOpenSessions()){
            try {
                s.getBasicRemote().sendText(broadcastMessage);
            } catch (IOException ex) {
                Logger.getRootLogger().info("Error in websocket broadcast : " + ex.getMessage());
            }
        }
        
        return "OK";
    }
 
    @OnOpen
    public void myOnOpen(Session session) {
        Logger.getRootLogger().info("WebSocket opened: " + session.getId());
    }
 
    @OnClose
    public void myOnClose(CloseReason reason) {
        Logger.getRootLogger().info("Closing a WebSocket due to " + reason.getReasonPhrase());
    }
}
