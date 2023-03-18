package com.example.hackathon.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userRequest_id")
    private User usersRequest;
    @ManyToOne
    @JoinColumn(name = "userReceive_id")
    private User usersReceive;
    // true la accept, false la waiting
    private boolean status;
}
