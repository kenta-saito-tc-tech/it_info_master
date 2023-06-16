package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.AccountDao;
import com.example.It_info_pass_master.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgAccountService implements AccountService{
    @Autowired
    private AccountDao accountDao;

    @Override
    public UserRecord findUser(IdPassRecord idPassRecord) {
        return accountDao.findUser(idPassRecord);
    }
    @Override
    public UserRecord checkIdExist(String id) {
        return accountDao.checkIdExist(id);
    }
    @Override
    public int insertUser(UserRecord userRecord) { return accountDao.insertUser(userRecord);}

    @Override
    public List<RankingRecord> findTopThree(String age) {
        return accountDao.findTopThree(age);
    }

    @Override
    public List<AgeRecord> findAgeAll() {
        return accountDao.findAgeAll();
    }

    @Override
    public MyRankRecord findUserRank(String age, int id) {
        return accountDao.findUserRank(age, id);
    }


}
