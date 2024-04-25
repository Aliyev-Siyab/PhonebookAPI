package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddNewContactTests extends TestBase {

    @Test
    public void AddNewContactPositiveTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Petrovich")
                .email("ssa@gm.com")
                .phone("1234567890")
                .address("Koeln")
                .description("adadasda")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");
    }

    @Test
    public void AddNewContactNegativeAnyFormatTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Petrovich")
                .email("ssa@gm.com")
                .phone("12345")
                .address("Koeln")
                .description("adadasda")
                .build();

        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");
    }

    @Test
    public void AddNewContactNegativeUnauthorizedTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Petrovich")
                .email("ssa@gm.com")
                .phone("1234567890")
                .address("Koeln")
                .description("adadasda")
                .build();

        given()
                .header(AUTH, "asf,af,asf")
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract().path("message");
    }

    @Test
    public void AddNewContactNegativeDuplicateContactFieldsTest(){
        ContactDto contactDto = ContactDto.builder()
                .name("Vasya")
                .lastName("Vasya")
                .email("ssa@gm.com")
                .phone("1234567890")
                .address("Vasya")
                .description("adadasda")
                .build();


        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(200);

        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(409);
    }

}
