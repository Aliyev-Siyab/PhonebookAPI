package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdTests extends TestBase {
    String id;

    @BeforeMethod
    public void precondition() {
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
        // System.out.println(message); // Contact was added! ID: 81613246-5c3d-4285-a792-180d39888b52

        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void deleteContactByIdSuccessTest() {
        String message = given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        System.out.println(message);
    }

    @Test
    public void deleteContactByIdSuccessTest2() {
        given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }


    @Test
    public void deleteContactByIdNegativeAnyFormatErrorTest() {
        String message = given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + id + "123")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");

        System.out.println(message);
    }

    @Test
    public void deleteContactByIdNegativeAnyFormatErrorTest2() {
        int inValidId = 123;
        given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + inValidId)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: "+ inValidId + " not found in your contacts!"));
    }


    @Test
    public void deleteContactByIdNegativeUnauthorizedTest() {
        given()
                .header(AUTH, "afaasfsffafa")
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401) // JWT strings must contain exactly 2 period characters. Found: 0
                .assertThat().body("message", equalTo("JWT strings must contain exactly 2 period characters. Found: 0"));
    }
}
