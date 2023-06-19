package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.QuestionDao;
import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgQuestionService implements QuestionService{
    @Autowired
    private QuestionDao questionDao;

    @Override
    public List<UserAgeRecord> ageFindAll(){
        return questionDao.ageFindAll();
    }

    @Override
    public List<QuestionRecord> selectQuestion(int ageId, int categoryId ){
        return questionDao.selectQuestion(ageId, categoryId );
    }
}
