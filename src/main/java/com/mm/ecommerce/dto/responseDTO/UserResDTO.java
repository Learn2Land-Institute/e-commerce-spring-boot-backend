package com.mm.ecommerce.dto.responseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Data
public class UserResDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;
    private String roleName;
   // private List<menuList> menuList = new ArrayList<>();

}
