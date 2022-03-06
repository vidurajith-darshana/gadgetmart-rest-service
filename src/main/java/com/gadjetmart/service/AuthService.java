package com.gadjetmart.service;

import com.gadjetmart.constants.AppConstants;
import com.gadjetmart.dto.ResponseDto;
import com.gadjetmart.dto.UserDto;
import com.gadjetmart.exception.types.EmailNotValidException;
import com.gadjetmart.repository.AuthRepository;
import com.gadjetmart.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @Author Vidurajith Darshana
     * this is a global search to find out the user by email
     * @param email
     * @return User
     */
    public User findUserByEmail(String email) {
        return authRepository.findFirstByEmail(email);
    }

    /**
     * @Author Vidurajith Darshana
     * This is for the signup purpose. just send email and password, will register a customer
     * to system
     * @param userDto
     * @return ResponseDto
     */
    public ResponseDto signUp(UserDto userDto){
        User user;
        if (userDto.getEmail().matches(AppConstants.emailValidationRegex)) {
            user = findUserByEmail(userDto.getEmail());
            if (user != null) {
                log.info("{}. tried to sign up again",user.getEmail());
                return new ResponseDto("500","That email is taken. Try another one!");
            } else {

                user = new User();
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setAddress(userDto.getAddress());

                authRepository.saveAndFlush(user);

                return new ResponseDto("success","200",null);
            }
        } else {
            throw new EmailNotValidException();
        }
    }

    /**
     * use to edit the user details
     * @param userDto
     * @return ResponseDto
     */
    public ResponseDto update(UserDto userDto){
        User user;
        if (userDto.getEmail().matches(AppConstants.emailValidationRegex)) {
            user = findUserByEmail(userDto.getEmail());
            if (user != null) {

                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setAddress(userDto.getAddress());

                authRepository.saveAndFlush(user);

                return new ResponseDto("success","200",null);
            } else {
                return new ResponseDto("user not found","500",null);
            }
        } else {
            throw new EmailNotValidException();
        }
    }

    /**
     * Use to get the user details
     * @param email
     * @return ResponseDto
     */
    public ResponseDto getUserDetails(String email){
        User user;
        if (email.matches(AppConstants.emailValidationRegex)) {
            user = findUserByEmail(email);
            if (user == null) {
                return new ResponseDto("500","User not found!");
            } else {

                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setEmail(user.getEmail());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setAddress(user.getAddress());

                return new ResponseDto("success","200",userDto);
            }
        } else {
            throw new EmailNotValidException();
        }
    }
}
