package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.RandomDao;
import com.example.It_info_pass_master.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgRandomService implements RandomService{
    @Autowired
    private RandomDao randomDao;

    @Override
    public List<CategoryRecord> findRandSelect(int ageId, int userId){
        return randomDao.findRandSelect(ageId, userId);
    }

    @Override
    public QuestionRecord selectRandom(Integer ageId, String[] categories,Integer perfect, Integer userId){
        return randomDao.selectRandom(ageId, categories, perfect, userId);
    }

    @Override
    public int categorySelectUpdate(UserCategoryRecord userCategoryRecord) {
        return randomDao.categorySelectUpdate(userCategoryRecord);
    }

    @Override
    public int sessionRandom(SelectRandomRecord selectRandomRecord){
        return randomDao.sessionRandom(selectRandomRecord);
    }

    @Override
    public ChoiceRecord findYourAnswer(int choiceId){
        return randomDao.findYourAnswer(choiceId);
    }

    @Override
    public int updateLookingBackCheck(int ageId, int questionId, int userId, int check){
        return randomDao.updateLookingBackCheck(ageId, questionId, userId, check);
    };

    @Override
    public int updatePerfectCheck(int ageId, int questionId, int userId, int perfectCheck1){
        return randomDao.updatePerfectCheck(ageId, questionId, userId, perfectCheck1);
    }

    public List<LookCheckRecord> findLookQuestion(int userId) {
        return randomDao.findLookQuestion(userId);
    }


    @Override
    //解説を取得するメソッド
    public List<QuestionRecord> findAnswerText(Integer questionId) {
        return randomDao.findAnswerText(questionId);
    }


    @Override
    //正解の選択肢を取得するメソッド
    public List<ChoiceRecord> findAnswer(Integer questionId) {
        return randomDao.findAnswer(questionId);
    }
}
