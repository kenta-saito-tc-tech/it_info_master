package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.ageidRecord;

import java.util.List;

public interface GameDao {
    public List<ageidRecord> userGameAdd(int userid, int ageid);

}
