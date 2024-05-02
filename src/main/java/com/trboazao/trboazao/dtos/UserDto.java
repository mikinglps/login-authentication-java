package com.trboazao.trboazao.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;



public record UserDto(
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String password,
        @Nullable
        String nome,
        @Nullable
        String jwtToken
) {
        public UserDto noPassword(){
                return new UserDto(email, null, nome, jwtToken);
        }
}
