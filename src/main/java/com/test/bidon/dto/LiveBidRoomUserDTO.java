package com.test.bidon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveBidRoomUserDTO {

    private Long partId;
    private Long userId;
    private String email;
    private String name;
    private String profile;
    private String national;
    private String tel;
    private Integer bidPrice;

    public LiveBidRoomUserDTO(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveBidRoomUserDTO user = (LiveBidRoomUserDTO) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

}
