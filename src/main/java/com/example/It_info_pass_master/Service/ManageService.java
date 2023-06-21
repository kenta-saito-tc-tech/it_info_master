package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ManageService {
    public QuestionRecord questionInsert(QuestionRecord questionRecord);
    public int choiceInsert(List<ChoiceRecord> choiceRecord);
}
