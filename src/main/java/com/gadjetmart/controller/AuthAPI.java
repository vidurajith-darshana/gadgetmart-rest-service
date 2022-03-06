package com.gadjetmart.controller;

import com.gadjetmart.configuration.JwtAuthenticationController;
import com.gadjetmart.configuration.JwtRequest;
import com.gadjetmart.configuration.JwtResponse;
import com.gadjetmart.dto.AuthDto;
import com.gadjetmart.dto.ResponseDto;
import com.gadjetmart.dto.UserDto;
import com.gadjetmart.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/api/v1")
public class AuthAPI {

    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    private AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid AuthDto authDto) {
        Map<String,Object> map = new HashMap<>();
        try {
            JwtRequest jwtRequest = new JwtRequest();
            jwtRequest.setUsername(authDto.getEmail());
            jwtRequest.setPassword(authDto.getPassword());

            JwtResponse jwtResponse = jwtAuthenticationController.generateAuthenticationToken(jwtRequest);

            map.put("id", authService.findUserByEmail(authDto.getEmail()).getId());
            map.put("token", jwtResponse.getToken());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto("error","500", "Incorrect email or password"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("success","200", map),HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(authService.signUp(userDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(authService.update(userDto), HttpStatus.OK);
    }

    @GetMapping("/details/{email:.+}")
    public ResponseEntity<ResponseDto> getUserDetails(@PathVariable("email") String email){
        return new ResponseEntity<>(authService.getUserDetails(email), HttpStatus.ACCEPTED);
    }
}
