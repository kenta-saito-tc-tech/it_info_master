package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.GameDao;
import com.example.It_info_pass_master.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgGameService implements GameService{
    @Autowired
    private GameDao gameDao;

    public ageidRecord userGameAdd(int userid, int ageid) {
        return gameDao.userGameAdd(userid, ageid);
    }

    public GameQuestionRecord gameAgeSelect(int ageId, int i) {
        return gameDao.gameAgeSelect(ageId, i);
    }

    public List<GameSelectRecord> gameChoiceSelect(int ageId, int i) {
        return gameDao.gameChoiceSelect(ageId, i);
    }

    @Override
    public FalseSumRecord userGameDetial(int dateId) {
        return gameDao.userGameDetial(dateId);
    }

    @Override
    public int gameTimeAdd(int id, int resultTime) {
        return gameDao.gameTimeAdd(id, resultTime);
    }

    @Override
    public int gameAnswerAdd(GameAnswerRecord gameAnswerRecord) {
        return gameDao.gameAnswerAdd(gameAnswerRecord);
    }

    public GameScoreRecord gameScoreSelect(int userGameId) {
        return gameDao.gameScoreSelect(userGameId);
    }

    @Override
    public List<GameResultListRecord> resultList(int userGameId) {
        return gameDao.resultList(userGameId);
    }

    @Override
    public QuestionRecord gameDetailQuestion(int questionId) {
        return gameDao.gameDetailQuestion(questionId);
    }

    @Override
    public List<ChoiceRecord> gameDetailChoice(int questionId) {
        return gameDao.gameDetailChoice(questionId);
    }
}
