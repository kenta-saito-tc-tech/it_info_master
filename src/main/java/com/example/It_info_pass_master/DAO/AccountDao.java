package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;

import java.util.List;

public interface AccountDao {
    public UserRecord findUser(IdPassRecord idPassRecord);
    public UserRecord checkIdExist(String id);
    public int insertUser(UserRecord userRecord);
    public List<RankingRecord> findTopThree(String age);
    public List<AgeRecord> findAgeAll();
    public MyRankRecord findUserRank(String age, int id);
}
