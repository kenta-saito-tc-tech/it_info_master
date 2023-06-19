package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;

import java.util.List;

public interface QuestionDao {
    List<UserAgeRecord> ageFindAll();

    List<QuestionRecord> selectQuestion(int ageId, int categoryId);
}
