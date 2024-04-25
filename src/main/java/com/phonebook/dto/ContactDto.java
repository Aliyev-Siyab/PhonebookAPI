package com.phonebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ContactDto {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String description;

}
//    id	string
//    name*	string
//    lastName*	string
//    email	string
//    phone	string
//    pattern: ^\d{10,15}$ ->> Это не надо так как это ограничения пароля
//    address*	string
//    description	string

