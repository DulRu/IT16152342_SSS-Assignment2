package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.apache.http.HttpResponse;
import java.io.Reader;
import org.apache.http.NameValuePair;
import java.net.URLEncoder;
import java.util.Base64;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

@WebServlet(name = "FBcallback")
public class FBcallback extends HttpServlet {
    public FBcallback() throws IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String Code = request.getParameter("code");
            if (Code != null) {
                String CLIENT_ID = "1987099361350841";
                String SECRET = "53a97082afc6268beb70fccb041eacbe";
                String GRANT_TYPE = "authorization_code";
                String TOKEN_ENDPOINT = "https://graph.facebook.com/oauth/access_token";
                String CALLBACK_URI = "https://localhost:8443/FBcallback";
                HttpPost httpCon = new HttpPost(TOKEN_ENDPOINT + "?" + "client_id=" + URLEncoder.encode(CLIENT_ID) + "&" + "code=" + URLEncoder.encode(Code) + "&" +
                        "redirect_uri=" + URLEncoder.encode(CALLBACK_URI) + "&" + "grant_type=" + URLEncoder.encode(GRANT_TYPE));
                byte[] header = (CLIENT_ID + ":" + APP_SECRET).getBytes();
                String enHeader = new String(Base64.getEncoder().encodeToString(header));
                httpCon.setHeader("Authorization", "Basic " +
                        enHeader);
                CloseableHttpClient httpClient =
                        HttpClients.createDefault();
                HttpResponse httpResponse = httpClient.execute(httpCon);
                Reader reader = new InputStreamReader
                        (httpResponse.getEntity().getContent());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                String accessToken = null;
                String[] responseProperties = line.split("&");
                for (String responseProperty : responseProperties) {
                    System.out.println(responseProperty);
                    try {
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(responseProperty);
                        JSONObject jsonobj = (JSONObject) obj;
                        Token = jsonobj.get("access_token").toString();
                        System.out.println("Access token: " + Token);

                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }

                }
                String requestUrl = "https://graph.facebook.com/v3.1/me?fields=id,name,gender";
                httpCon = new HttpPost(requestUrl);
                httpCon.addHeader("Authorization", "Bearer " + Token);
                List<NameValuePair> urlParameters = new
                        ArrayList<NameValuePair>();
                urlParameters.add(new BasicNameValuePair("method", "get"));
                httpCon.setEntity(new UrlEncodedFormEntity(urlParameters));
                httpResponse = httpClient.execute(httpCon);
                bufferedReader = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));
                String Fbfeed = bufferedReader.readLine();
                System.out.println("Response-----> " + Fbfeed);
                httpClient.close();
            }
        }

     catch(Exception e){
        e.printStackTrace();
    }
  }
}
