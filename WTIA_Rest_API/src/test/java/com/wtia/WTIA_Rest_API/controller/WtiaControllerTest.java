package com.wtia.WTIA_Rest_API.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class WtiaControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new WtiaController()).build();
    }


    @Test
    public void testWTIADetails() throws Exception {

        
        //expected
        String expectedTimezoneId = null;
        StringBuilder responseData = new StringBuilder();
        JsonObject expectedJsonObject = null;
        //URL url = new URL("https://api.wheretheiss.at/v1/satellites/25544");
        //URL url = new URL("https://api.wheretheiss.at/v1/coordinates/37.795517,-122.393693");
        URL url = new URL("https://api.wheretheiss.at/v1/coordinates/50.11496269845,118.07900427317");
        //URL url = new URL("https://api.coincap.io/v2/assets/ethereum");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {

            String line;

            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }

            expectedJsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
            expectedTimezoneId = expectedJsonObject.get("timezone_id").toString();
            expectedTimezoneId = expectedTimezoneId.replaceAll("^\"|\"$", "");

        }

        //actual
        MvcResult result = mockMvc.perform(get("/getSateliteDetailsByLatAndLon?latitude=50.11496269845&longitude=118.07900427317"))
                .andReturn();
        String recievedResponse = result.getResponse().getContentAsString();
        JsonObject actualJsonObject = new Gson().fromJson(recievedResponse, JsonObject.class);
        String actualTimezoneId = actualJsonObject.get("timezone_id").toString();
        actualTimezoneId = actualTimezoneId.replaceAll("^\"|\"$", "");

        assertEquals(expectedTimezoneId, actualTimezoneId);
    }

//    @Test
//    public void testAge() throws Exception {
//
//        String name = "Snow";
//
//        //expected
//        String expectedAgeInString = null;
//        int expectedAge;
//        StringBuilder responseData = new StringBuilder();
//        JsonObject expectedJsonObject = null;
//        URL url = new URL("https://api.agify.io/?name=" + name);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        try (BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()))) {
//
//            String line;
//
//            while ((line = in.readLine()) != null) {
//                responseData.append(line);
//            }
//
//            expectedJsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
//            expectedAgeInString = expectedJsonObject.get("age").toString();
//            expectedAge = Integer.parseInt(expectedAgeInString);
//
//        }
//
//        //actual
//        MvcResult result = mockMvc.perform(get("/getProfile?name=" + name))
//                .andReturn();
//
//        String recievedResponse = result.getResponse().getContentAsString();
//        JsonObject jsonObject = new Gson().fromJson(recievedResponse, JsonObject.class);
//
//        int age = Integer.parseInt(jsonObject.get("age").toString());
//
//        assertEquals(expectedAge, age);
//    }
}
