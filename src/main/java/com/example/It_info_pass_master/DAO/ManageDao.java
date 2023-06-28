package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ManageDao {
    public QuestionRecord questionInsert(QuestionRecord questionRecord);
    public int choiceInsert(List<ChoiceRecord> choiceRecord);
    public List<UserAgeRecord> adminAllAgeSelect();
    public List<QuestionRecord> adminAllQuestionSelect();
    public List<AdminQuestionRecord> adminCheckAge(Integer age);
    public int adminSetQuestion(List<AdminQuestionRecord> setQuestion);
    public List<AdminQuestionRecord> adminCheckGameAge(Integer age);
    public int adminSetGameQuestion(List<AdminQuestionRecord> setQuestion);
    public Integer adminAddAge(Integer age);
    public int adminAddQuestion(List<AdminQuestionRecord> setQuestion);
    public List<QuestionIdRecord> adminCheckImpossible(int ageId);
}
