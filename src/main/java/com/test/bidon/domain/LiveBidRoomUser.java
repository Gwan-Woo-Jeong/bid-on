package com.test.bidon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveBidRoomUser {

    private Long partId;
    private Long userId;
    private String email;
    private String name;
    private String profile;
    private String national;
    private String tel;
    private Boolean isHighestBidder;

    public LiveBidRoomUser(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveBidRoomUser user = (LiveBidRoomUser) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

}
