package com.gadjetmart.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthDto {

    @NotBlank(message = "Email is required!")
    @Size(min = 4,max = 255,message = "Email should have 4 to 255 characters")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 4,max = 255,message = "Password should have 4 to 255 characters")
    private String password;
}