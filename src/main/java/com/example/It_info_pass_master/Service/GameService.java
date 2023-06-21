package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.*;

import java.util.List;

public interface GameService {
    public ageidRecord userGameAdd(int userid, int ageid);
    public GameQuestionRecord gameAgeSelect(int ageId, int i);
    public List<GameSelectRecord> gameChoiceSelect(int ageId, int i);
    public FalseSumRecord userGameDetial(int dateId);
    public int gameAnswerAdd(GameAnswerRecord gameAnswerRecord);
    public int gameTimeAdd(int id, int resultTime);
    public GameScoreRecord gameScoreSelect(int userGameId);
    public List<GameResultListRecord> resultList(int userGameId);
    public QuestionRecord gameDetailQuestion(int questionId);
    public List<ChoiceRecord> gameDetailChoice(int questionId);
}
