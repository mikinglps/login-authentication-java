package com.trboazao.trboazao.controllers;

import com.trboazao.trboazao.dtos.UserDto;
import com.trboazao.trboazao.models.UserModel;
import com.trboazao.trboazao.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user/")
public class  UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto){
        UserDto response = userService.convertToDto(userService.add(userDto));

        if(response != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
            return ResponseEntity.badRequest().build();
        }

    }


    @PostMapping("/login")
    ResponseEntity<UserDto> login(@RequestBody UserDto userDto){
        UserDto userDtoResponse = userService.convertToDto(userService.login(userDto.email(), userDto.password()));
        return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse.noPassword());


    }

}
