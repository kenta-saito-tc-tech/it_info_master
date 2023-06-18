package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.GameQuestionRecord;
import com.example.It_info_pass_master.Entity.ageidRecord;
import com.example.It_info_pass_master.Entity.GameSelectRecord;

import java.util.List;

public interface GameService {
    public ageidRecord userGameAdd(int userid, int ageid);
    public GameQuestionRecord gameAgeSelect(int ageId, int i);
    public GameSelectRecord gameChoiceSelect(int ageId, int i);
}
