package com.test.bidon.repository;

import com.test.bidon.domain.LiveBidRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class LiveBidRoomRepository {

    private final Map<Long, LiveBidRoom> bidRooms = new HashMap<>();

    public void save(Long roomId, LiveBidRoom bidRoom) {
        bidRooms.put(roomId, bidRoom);
    }

    public LiveBidRoom findById(Long roomId) {
        return bidRooms.get(roomId);
    }

    public List<LiveBidRoom> findAll() {
        return new ArrayList<>(bidRooms.values());
    }
}