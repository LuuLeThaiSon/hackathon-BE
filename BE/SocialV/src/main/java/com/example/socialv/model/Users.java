package com.example.socialv.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Valid
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp="^[a-zA-Z0-9]*$",message = "Tên chứa ký tự đặc biệt!")
    private String username;
    @Size(min = 6,max = 32,message = "password từ 6-32 ký tự")
    @Pattern(regexp="^[a-zA-Z0-9]*$",message = "Tên chứa ký tự đặc biệt!")
    private String password;
    @Size(min = 6,max = 32,message = "password từ 6-32 ký tự")
    @Pattern(regexp="^[a-zA-Z0-9]*$",message = "Tên chứa ký tự đặc biệt!")
    private String confirmPassword;
    @NotNull(message = "không được bỏ trống")
    private String name;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$",message = "phải là số có 10 số")
    private String phone;
    @NotNull(message = "không được bỏ trống")
    private LocalDate birthday;
    //status block hoac active( default true)
    private boolean status;
    //default false , khi dang nhap thi chuyen thanh true, log out thi thanh false
    private boolean checkOn;
    // default true: cho xem bạn bè
    private boolean seeFriendPermission;
    //default true: cho người lạ comment

    private boolean commentPermission;
    private String avatar;
    private String address;
    private String hobby;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
