package com.github.raydive;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class TypetalkDirectMessage {
    private static final String OAUTH_URL = "https://typetalk.com/oauth2/access_token";
    private static final String DM_URL = "https://typetalk.com/api/v1/messages/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String args[]) {
        try {
            String token = getOauthToken();
            String json = "{\"message\": \"hello!\"}";
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().
                    url(DM_URL + "@" + System.getenv("TO_USER")).
                    header("Authorization", token).
                    post(body).
                    build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getOauthToken() throws IOException {
        RequestBody body = new FormBody.Builder().
                add("client_id", System.getenv("TYPETALK_CLIENT_ID")).
                add("client_secret", System.getenv("TYPETALK_CLIENT_SECRET")).
                add("grant_type", "client_credentials").add("scope", "topic.post").
                build();
        Request request = new Request.Builder().url(OAUTH_URL).post(body).build();
        Response response = client.newCall(request).execute();
        String ret = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        Token token = mapper.readValue(ret, Token.class);

        return token.toString();
    }
}

class Token {
    public String access_token;
    public String expires_in;
    public String scope;
    public String refresh_token;
    public String token_type;

    @Override
    public String toString() {
        return token_type + " " + access_token;
    }
}
