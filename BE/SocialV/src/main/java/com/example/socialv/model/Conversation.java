package com.example.socialv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // type = 1 là cuộc hội thoại cá nhân, type = 2 là cuộc hội thoại nhóm
    private Integer type;
    private String name;
    private boolean status;
}
