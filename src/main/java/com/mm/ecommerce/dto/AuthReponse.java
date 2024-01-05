package com.mm.ecommerce.dto;

import com.mm.ecommerce.enums.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthReponse {
    private String email;
    private String token;
    private String userRole;
}
