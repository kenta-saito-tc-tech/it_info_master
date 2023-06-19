package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.*;

import java.util.List;

public interface GameService {
    public ageidRecord userGameAdd(int userid, int ageid);
    public GameQuestionRecord gameAgeSelect(int ageId, int i);
    public List<GameSelectRecord> gameChoiceSelect(int ageId, int i);
    public falseSumRecord userGameDetial(int dateId);

    public GameScoreRecord gameScoreSelect(int userGameid);
}
