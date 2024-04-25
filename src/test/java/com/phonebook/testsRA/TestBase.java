package com.phonebook.testsRA;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoic2l5YWJ0ZXN0MUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcxNDYzODgwOCwiaWF0IjoxNzE0MDM4ODA4fQ.2ZJLhqOXF0Ip26WpgRlffw9EV13n4ATamWXNG2Q2L7A";
    public static final String AUTH = "Authorization";

    @BeforeMethod
    public void init(){
        RestAssured.baseURI="https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath="v1";
    }
}
