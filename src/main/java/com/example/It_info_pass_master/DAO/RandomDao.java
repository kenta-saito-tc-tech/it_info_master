package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;

import java.util.List;

public interface RandomDao {
    List<CategoryRecord> findRandSelect(int ageId, int userId);

    QuestionRecord selectRandom(Integer ageId, String[] categories,Integer perfect, Integer userId);

    int categorySelectUpdate(UserCategoryRecord userCategoryRecord);

    int sessionRandom(SelectRandomRecord selectRandomRecord);

    ChoiceRecord findYourAnswer(int choiceId);

    //見返しリストをアップデートする処理
    int updateLookingBackCheck(int ageId, int questionId, int userId, int check);


    int updatePerfectCheck(int ageId, int questionId, int userId, int perfectCheck1);

    List<LookCheckRecord> findLookQuestion(int userId);

    //解説を取得するメソッド
    List<QuestionRecord> findAnswerText(Integer questionId);

    //正解の選択肢を取得するメソッド
    List<ChoiceRecord> findAnswer(Integer questionId);
}
