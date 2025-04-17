package com.example.linkedin.dto;

import java.time.Instant;

public record LoginResponse(String token, Long expiresIn) {
}
