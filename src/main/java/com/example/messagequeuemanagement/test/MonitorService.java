package com.example.messagequeuemanagement.test;

import org.springframework.stereotype.Service;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

@Service
public class MonitorService {

    public String[] listMonitors() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        String[] monitorNames = new String[gs.length];

        for (int i = 0; i < gs.length; i++) {
            monitorNames[i] = gs[i].getIDstring();
        }

        return monitorNames;
    }

    public String[] getConnectedMonitors() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        String[] monitorNames = new String[gs.length];

        for (int i = 0; i < gs.length; i++) {
            monitorNames[i] = gs[i].getIDstring();
        }

        return monitorNames;
    }
}
