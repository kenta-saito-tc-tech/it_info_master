package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.ageRecord;

import java.util.List;

public interface QuestionService {
    List<ageRecord> ageFindAll();

    List<QuestionRecord> selectQuestion(int ageId, int categoryId);
}
