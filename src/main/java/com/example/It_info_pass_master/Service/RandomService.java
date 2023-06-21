package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.SelectRandomRecord;
import com.example.It_info_pass_master.Entity.CategoryRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserCategoryRecord;

import java.util.List;

public interface RandomService {

    List<CategoryRecord> findRandSelect(int ageId, int userId);

    List<QuestionRecord> selectRandom(SelectRandomRecord selectRandomRecord);

    int categorySelectUpdate(UserCategoryRecord userCategoryRecord);
}
