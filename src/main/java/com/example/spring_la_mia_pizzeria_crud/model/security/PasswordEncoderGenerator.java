package com.example.spring_la_mia_pizzeria_crud.model.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("tuapassword"));
        System.out.println(encoder.encode("ciaone"));
    }
}
