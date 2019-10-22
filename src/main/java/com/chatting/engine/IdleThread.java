package com.chatting.engine;

public class IdleThread extends Thread {
    Session session;

    public IdleThread(Session session) {
        this.session = session;
    }

    public void run() {
        try {
            Thread.sleep(3000);
            if (session.getConnections() > 0) {
                session.setStatus(SessionStatus.online);
            } else {
                session.setStatus(SessionStatus.offline);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
