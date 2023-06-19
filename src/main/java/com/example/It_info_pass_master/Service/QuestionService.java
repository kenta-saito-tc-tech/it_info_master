package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;

import java.util.List;

public interface QuestionService {
    List<UserAgeRecord> ageFindAll();

    List<QuestionRecord> selectQuestion(int ageId, int categoryId);
}
