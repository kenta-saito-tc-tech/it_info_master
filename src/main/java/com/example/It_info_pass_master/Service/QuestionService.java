package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;

import java.util.List;

public interface QuestionService {
    List<UserAgeRecord> ageFindAll();

    List<QuestionRecord> selectQuestion(int ageId, int categoryId);

    Integer selectPerfectCheck(int userId, int ageId, int questionId);

    QuestionRecord findQuestion(int id);

    List<ChoiceRecord> findChoices(int id);

    String findAnswer(int id);

    int checkComplete(int id, int userId, int ageId);

    int checkNotComplete(int id, int userId, int ageId);

    int checkCompleteCheck(int id, int userId,int ageId);

    //user_checkテーブルを確認＆作成するメソッド
     int findCheckUser(int userId, int questionId, int ageId);


    String findAge(int ageId);

    int checkLookCheck(int id, int userId, int ageId);

    int findQuestionId(String title);

    String findCategory(int categoryId);
}
