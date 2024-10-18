package com.example.crud.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
