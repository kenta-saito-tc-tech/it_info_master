package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;

import java.util.List;

public interface QuestionService {
    List<UserAgeRecord> ageFindAll();

    List<QuestionRecord> selectQuestion(int ageId, int categoryId);

    QuestionRecord findQuestion(int id);

    List<ChoiceRecord> findChoices(int id);

    String findAnswer(int id);

    int checkComplete(int id, int userId);

    int checkNotComplete(int id, int userId);

    int checkCompleteCheck(int id, int userId);
}
