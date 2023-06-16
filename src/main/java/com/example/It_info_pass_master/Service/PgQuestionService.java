package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.QuestionDao;
import com.example.It_info_pass_master.Entity.ageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgQuestionService implements QuestionService{
    @Autowired
    private QuestionDao questionDao;

    @Override
    public List<ageRecord> ageFindAll(){
        return questionDao.ageFindAll();
    }
}
