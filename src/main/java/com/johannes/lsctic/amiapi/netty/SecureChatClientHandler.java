/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johannes.lsctic.amiapi.netty;

/**
 * @author Johannes
 */

import com.google.common.eventbus.EventBus;
import com.johannes.lsctic.panels.gui.fields.callrecordevents.AddCdrAndUpdateEvent;
import com.johannes.lsctic.panels.gui.fields.otherevents.SetStatusEvent;
import com.johannes.lsctic.panels.gui.fields.serverconnectionhandlerevents.UserLoginStatusEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<String> {
    private EventBus bus;
    private String ownExtension;

    public SecureChatClientHandler(EventBus bus, String ownExtension) {
        this.bus = bus;
        this.ownExtension = ownExtension;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Logger.getLogger(getClass().getName()).info(msg);
        if (msg.startsWith("login:success")) {
            Logger.getLogger(getClass().getName()).info(msg.substring(13));
            bus.post(new UserLoginStatusEvent(true, msg.substring(13)));
        } else if (msg.equals("login:failed")) {
            bus.post(new UserLoginStatusEvent(false, ""));
        } else {
            try {
                String chatInput = msg;
                int op = Integer.valueOf(chatInput.substring(0, 3));
                String param = chatInput.substring(3, chatInput.length());
                switch (op) {
                    case 0: {
                        updateStatus(param);
                        break;
                    }
                    case 10: {
                        createAndPropagateCdrField(param);
                        break;
                    }
                    default: {
                        Logger.getLogger(getClass().getName()).log(Level.WARNING, "Command not recognized");
                        break;
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private void updateStatus(String param) {
        String[] d = param.split(":");
        String intern = d[0];
        int state = Integer.parseInt(d[1]);
        bus.post(new SetStatusEvent(state, intern));
        Logger.getLogger(getClass().getName()).log(Level.INFO, "New State");
    }

    private void createAndPropagateCdrField(String param) {
        String[] d = param.split(":");
        String source = d[0];
        String destination = d[1];
        Date startTime = new Date(Long.parseLong(d[2]));
        Long duration = Long.parseLong(d[3]);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "New CDR");
        Platform.runLater(() -> {
            if (source.equals(ownExtension)) {
                bus.post(new AddCdrAndUpdateEvent(destination, startTime.toString(), duration.toString(), true));
            } else {
                bus.post(new AddCdrAndUpdateEvent(destination, startTime.toString(), duration.toString(), false));
            }
        });
    }

}
