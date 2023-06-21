package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.ManageDao;
import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgManageService implements ManageService{
    @Autowired
    private ManageDao manageDao;

    @Override
    public QuestionRecord questionInsert(QuestionRecord questionRecord) {
        return manageDao.questionInsert(questionRecord);
    }

    @Override
    public int choiceInsert(List<ChoiceRecord> choiceRecord) {
        return manageDao.choiceInsert(choiceRecord);
    }

}
