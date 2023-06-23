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
    public List<UserAgeRecord> findAgeAll() {
        return accountDao.findAgeAll();
    }

    @Override
    public MyRankRecord findUserRank(String age, int id) {
        return accountDao.findUserRank(age, id);
    }

    @Override
    public int userNameUpdate(UserRecord userRecord) {
        return accountDao.userNameUpdate(userRecord);
    }

    @Override
    public int userPassUpdate(UserRecord userRecord) {
        return accountDao.userPassUpdate(userRecord);
    }

    @Override
    public int userDelete(UserRecord userRecord) {
        return accountDao.userDelete(userRecord);
    }

    @Override
    public List<UserRecord> findAllUser() {
        return accountDao.findAllUser();
    }

    @Override
    public int insertInquiry(InquiryRecord insertInquiry) {
        return accountDao.insertInquiry(insertInquiry);
    }

    @Override
    public List<InquiryRecord> findAllInquiry(int id) {
        return accountDao.findAllInquiry(id);
    }

    @Override
    public InquiryRecord inquiryFindById(int id) {
        return accountDao.inquiryFindById(id);
    }

    @Override
    public List<InquiryAdminRecord> findAllInquiry() {
        return accountDao.findAllInquiry();
    }

    @Override
    public int updateAnswerInquiry(InquiryRecord inquiryRecord) {
        return accountDao.updateAnswerInquiry(inquiryRecord);
    }

    @Override
    public int updateReadInquiry(int id) {
        return accountDao.updateReadInquiry(id);
    }


}
