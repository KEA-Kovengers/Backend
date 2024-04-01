package com.newcord.userservice.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FriendId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id1")
    private Users userId1;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private Users userId2;
}