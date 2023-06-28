package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.ManageDao;
import com.example.It_info_pass_master.Entity.*;
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

    @Override
    public List<UserAgeRecord> adminAllAgeSelect() {
        return manageDao.adminAllAgeSelect();
    }

    @Override
    public List<QuestionRecord> adminAllQuestionSelect() {
        return manageDao.adminAllQuestionSelect();
    }

    @Override
    public List<AdminQuestionRecord> adminCheckAge(Integer age){
        return manageDao.adminCheckAge(age);
    }

    @Override
    public int adminSetQuestion(List<AdminQuestionRecord> setQuestion) {
        return manageDao.adminSetQuestion(setQuestion);
    }

    @Override
    public List<AdminQuestionRecord> adminCheckGameAge(Integer age){
        return manageDao.adminCheckGameAge(age);
    }

    @Override
    public int adminSetGameQuestion(List<AdminQuestionRecord> setQuestion) {
        return manageDao.adminSetGameQuestion(setQuestion);
    }
    @Override
    public Integer adminAddAge(Integer age) {
        return manageDao.adminAddAge(age);
    }

    @Override
    public int adminAddQuestion(List<AdminQuestionRecord> setQuestion) {
        return manageDao.adminAddQuestion(setQuestion);
    }

    @Override
    public List<QuestionIdRecord> adminCheckImpossible(int ageId) {
        return manageDao.adminCheckImpossible(ageId);
    }


}
