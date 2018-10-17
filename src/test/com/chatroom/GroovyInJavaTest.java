package com.chatroom;

import org.junit.Test;

public class GroovyInJavaTest {
    @Test
    public void accessProperty() {
        Message message = new Message();
        System.out.printf(message.getInfo().toString());
    }
}
