package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ManageDao {
    public QuestionRecord questionInsert(QuestionRecord questionRecord);
    public int choiceInsert(List<ChoiceRecord> choiceRecord);

}
