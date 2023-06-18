package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.GameDao;
import com.example.It_info_pass_master.Entity.GameQuestionRecord;
import com.example.It_info_pass_master.Entity.ageidRecord;
import com.example.It_info_pass_master.Entity.GameSelectRecord;
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

    public GameSelectRecord gameChoiceSelect(int ageId, int i) {
        return gameDao.gameChoiceSelect(ageId, i);
    }
}
