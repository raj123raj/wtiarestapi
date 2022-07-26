package com.wtia.WTIA_Rest_API.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class WtiaController {

    @RequestMapping("/getSateliteDetailsByLatAndLon")
    public @ResponseBody
    JsonObject getSateliteDetails(String  latitude,String longitude ) throws IOException {

        JsonObject jsonObject = new JsonObject();
        String timezoneId = getSateliteData(latitude,longitude);
        timezoneId = timezoneId.replaceAll("^\"|\"$", "");

        jsonObject.addProperty("timezone_id", timezoneId);

        return jsonObject;
    }

    private String getSateliteData(String latitude,String longitude) throws IOException {

        String data = null;
        StringBuilder responseData = new StringBuilder();
        JsonObject jsonObject = null;

        URL url = null;
        
        url = new URL("https://api.wheretheiss.at/v1/coordinates/"+latitude + "," + longitude);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {


            String line;

            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }

            jsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
            
            data = jsonObject.get("timezone_id").toString();


        }
        System.out.println(data);
        return data;
    }
}