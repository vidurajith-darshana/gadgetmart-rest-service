package com.gadjetmart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    private long id;

    @Size(min = 4,max = 255,message = "Email should have 4 to 255 characters")
    private String email;

    @NotBlank(message = "First name is required!")
    @Size(min = 4,max = 255,message = "First name should have 4 to 255 characters")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(min = 4,max = 255,message = "Last name should have 4 to 255 characters")
    private String lastName;

    @NotBlank(message = "Address is required!")
    @Size(min = 4,max = 500,message = "Address should have 4 to 500 characters")
    private String address;

    @Size(min = 4,max = 255,message = "Password should have 4 to 255 characters")
    private String password;

}
