package org.example.dao;


import org.example.Models.RoomType;

import java.util.List;

public interface RoomTypeDAO {
    RoomType findById(int roomTypeID);
    List<RoomType> findAll();
    void save(RoomType roomType);
    void update(RoomType roomType);
    void delete(int roomTypeID);
}
