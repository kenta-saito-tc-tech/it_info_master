package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.GameQuestionRecord;
import com.example.It_info_pass_master.Entity.GameSelectRecord;
import com.example.It_info_pass_master.Entity.ageidRecord;

import java.util.List;

public interface GameDao {
    public ageidRecord userGameAdd(int userid, int ageid);
    public GameQuestionRecord gameAgeSelect(int ageId, int i);
    public GameSelectRecord gameChoiceSelect(int ageId, int i);
}
