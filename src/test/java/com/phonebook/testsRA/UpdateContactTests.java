package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateContactTests extends TestBase {

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
    public void updateContactByIdPositiveTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id) // Указываем ID контакта, который нужно обновить
                .name("NewName")
                .lastName("NewLastName")
                .email("newemail@gm.com")
                .phone("9876543210")
                .address("NewAddress")
                .description("NewDescription")
                .build();

        //  запрос на обновление контакта
        given()
                .header(AUTH, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .put("contacts/") // EndPoint
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was updated"));

    }

    @Test
    public void updateContactByIdNegativeUnauthorizedTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id) // Указываем ID контакта, который нужно обновить
                .name("NewName")
                .lastName("NewLastName")
                .email("newemail@gm.com")
                .phone("9876543210")
                .address("NewAddress")
                .description("NewDescription")
                .build();

        given()
                .header(AUTH, "adadawdaw")
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .put("contacts/") // EndPoint
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("JWT strings must contain exactly 2 period characters. Found: 0"));
    }


    @Test
    public void updateContactByIdNegativeContactNotFoundTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id("1313131313") // Указываем ID контакта, который нужно обновить
                .name("NewName")
                .lastName("NewLastName")
                .email("newemail@gm.com")
                .phone("9876543210")
                .address("NewAddress")
                .description("NewDescription")
                .build();

        //  запрос на обновление контакта
        given()
                .header(AUTH, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .put("contacts/") // EndPoint
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: 1313131313 not found in your contacts!"));

    }
}
