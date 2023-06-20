package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.QuestionDao;
import com.example.It_info_pass_master.Entity.ChoiceRecord;
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

    @Override
    public QuestionRecord findQuestion(int id){
        return questionDao.findQuestion(id);
    }

    @Override
    public List<ChoiceRecord> findChoices(int id){
        return questionDao.findChoices(id);
    }

    @Override
    public String findAnswer(int id){
        return questionDao.findAnswer(id);
    }

    @Override
    public int checkComplete(int id, int userId){
        return questionDao.checkComplete(id, userId);
    }

    @Override
    public int checkNotComplete(int id, int userId){
        return questionDao.checkNotComplete(id, userId);
    }

    @Override
    public int checkCompleteCheck(int id, int userId){
        return questionDao.checkCompleteCheck(id, userId);
    }
}
