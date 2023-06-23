package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AccountDao {
    public UserRecord findUser(IdPassRecord idPassRecord);
    public UserRecord checkIdExist(String id);
    public int insertUser(UserRecord userRecord);
    public List<RankingRecord> findTopThree(String age);
    public List<UserAgeRecord> findAgeAll();
    public MyRankRecord findUserRank(String age, int id);
    public int userNameUpdate(UserRecord userRecord);
    public int userPassUpdate(UserRecord userRecord);
    public int userDelete(UserRecord userRecord);
    public List<UserRecord> findAllUser();
    public int insertInquiry(InquiryRecord insertInquiry);
    public List<InquiryRecord> findAllInquiry(int id);
    public InquiryRecord inquiryFindById(int id);
    public List<InquiryAdminRecord> findAllInquiry();
    public int updateAnswerInquiry(InquiryRecord inquiryRecord);
    public int updateReadInquiry(int id);
}
