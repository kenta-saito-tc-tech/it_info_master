package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.AdminQuestionRecord;
import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserAgeRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ManageService {
    public QuestionRecord questionInsert(QuestionRecord questionRecord);
    public int choiceInsert(List<ChoiceRecord> choiceRecord);
    public List<UserAgeRecord> adminAllAgeSelect();
    public List<QuestionRecord> adminAllQuestionSelect();
    public List<AdminQuestionRecord> adminCheckAge(Integer age);
    public int adminSetQuestion(List<AdminQuestionRecord> setQuestion);
    public List<AdminQuestionRecord> adminCheckGameAge(Integer age);
    public int adminSetGameQuestion(List<AdminQuestionRecord> setQuestion);
}
