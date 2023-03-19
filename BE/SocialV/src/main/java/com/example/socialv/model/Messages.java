package com.example.socialv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private LocalDateTime textAt;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
}
