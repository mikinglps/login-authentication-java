package com.trboazao.trboazao.services;

import com.trboazao.trboazao.dtos.UserDto;
import com.trboazao.trboazao.models.UserModel;
import com.trboazao.trboazao.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserModel add(UserDto userDto){
        if (userDto == null || userDto.email() == null || userDto.password() == null || userDto.nome() == null) {
            throw new IllegalArgumentException("Dados não pode ser vazios e todos os campos obrigatórios devem ser preenchidos.");
        }
        UserModel user = modelMapper.map(userDto, UserModel.class);
        String encryptedPwd = passwordEncoder.encode(userDto.password());
        user.setPassword(encryptedPwd);
        user.setNome(userDto.nome());
        user.setEmail(userDto.email());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        user.setJwtToken(jwtToken);
        return user;
    }

    public UserDto convertToDto(UserModel userModel){
        return new UserDto(userModel.getEmail(), userModel.getPassword(), userModel.getNome(), userModel.getJwtToken());
    }

    public UserModel login(String email, String senha) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        senha
                )
        );
        var user = userRepository.findByEmail(email).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        user.setJwtToken(jwtToken);
        return user;
    }
}
