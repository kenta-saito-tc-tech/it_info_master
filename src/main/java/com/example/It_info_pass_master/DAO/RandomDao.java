package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.SelectRandomRecord;
import com.example.It_info_pass_master.Entity.CategoryRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserCategoryRecord;

import java.util.List;

public interface RandomDao {
    List<CategoryRecord> findRandSelect(int ageId, int userId);

    QuestionRecord selectRandom(Integer ageId, String[] categories,Integer perfect, Integer userId);

    int categorySelectUpdate(UserCategoryRecord userCategoryRecord);

    int sessionRandom(SelectRandomRecord selectRandomRecord);
}
