package com.example.socialv.model;

import lombok.Data;

@Data
public class UserUpdate {
    private Long id;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
