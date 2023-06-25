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
    public Integer selectPerfectCheck(int userId, int ageId, int questionId) {
        return questionDao.selectPerfectCheck(userId, ageId, questionId);
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
    public int checkComplete(int id, int userId, int ageId){
        return questionDao.checkComplete(id, userId, ageId);
    }

    @Override
    public int checkNotComplete(int id, int userId, int ageId){
        return questionDao.checkNotComplete(id, userId, ageId);
    }

    @Override
    public int checkCompleteCheck(int id, int userId, int ageId){
        return questionDao.checkCompleteCheck(id, userId, ageId);
    }
    @Override
    //user_checkテーブルを確認＆作成するメソッド
    public int findCheckUser(int userId, int questionId, int ageId){
        return questionDao.findCheckUser(userId, questionId, ageId);
    }
    @Override
    public String findAge(int ageId){
        return questionDao.findAge(ageId);
    }

    @Override
    public int checkLookCheck(int id, int userId, int ageId){
        return questionDao.checkLookCheck(id, userId, ageId);
    }

    @Override
    public int findQuestionId(String title){
        return questionDao.findQuestionId(title);
    }

    public String findCategory(int categoryId){
        return questionDao.findCategory(categoryId);
    }

}
