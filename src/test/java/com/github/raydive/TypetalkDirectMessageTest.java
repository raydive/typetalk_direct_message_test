package com.github.raydive;

import org.junit.Test;

import static org.junit.Assert.*;

public class TypetalkDirectMessageTest {
    @Test
    public void getOauthToken() throws Exception {
        String token = TypetalkDirectMessage.getOauthToken();
        System.out.print(token);
        assertTrue("has \"Bearer\" token_type", token.contains("Bearer"));
    }

}