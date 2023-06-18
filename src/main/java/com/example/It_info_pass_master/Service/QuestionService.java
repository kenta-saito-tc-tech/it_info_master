package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.AgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;

import java.util.List;

public interface QuestionService {
    List<AgeRecord> ageFindAll();

    List<QuestionRecord> selectQuestion(int ageId, int categoryId);
}
