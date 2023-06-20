package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgGameDao implements GameDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ageidRecord userGameAdd(int userid, int ageid) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userid",userid);
        param.addValue("ageid",ageid);
        var id = jdbcTemplate.query("INSERT INTO user_game (game_date, user_id, age_id) VALUES (CURRENT_TIMESTAMP, :userid, :ageid) RETURNING id", param,new DataClassRowMapper<>(ageidRecord.class));
        return id.isEmpty() ? null : id.get(0);
    }

    public GameQuestionRecord gameAgeSelect(int ageId, int i) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId",ageId);
        param.addValue("i",i);
        var id = jdbcTemplate.query("  select A.age, G.question_id, C.category_name, Q.question_name, Q.question_text from game_age as G \n" +
                "  join age as A on G.age_id = A.id \n" +
                "  join questions as Q on G.question_id = Q.id \n" +
                "  join category as C on Q.category_id = C.id \n" +
                "  where G.age_id = (select age_id from user_game where id = :ageId) \n" +
                "  and G.question_id = (select question_id from game_age offset :i limit 1)"
                , param, new DataClassRowMapper<>(GameQuestionRecord.class));
        return id.isEmpty() ? null : id.get(0);
    }

    public List<GameSelectRecord> gameChoiceSelect(int ageId, int i) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId",ageId);
        param.addValue("i",i);
        var id = jdbcTemplate.query("select choice_text, answer from choice \n" +
                        "  join game_age on choice.question_id = game_age.question_id \n" +
                        "  where game_age.age_id = (select age_id from user_game where id = :ageId) \n" +
                        "  and game_age.question_id = (select question_id from game_age offset :i limit 1)"
                , param,new DataClassRowMapper<>(GameSelectRecord.class));
        return id;
    }

    @Override
    public FalseSumRecord userGameDetial(int dateId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("dateId", dateId);
        var id = jdbcTemplate.query("select count(*) from user_game_detail where date_id = :dateId and user_answer = 0"
                , param,new DataClassRowMapper<>(FalseSumRecord.class));
        return id.isEmpty() ? null : id.get(0);
    }

    @Override
    public int gameAnswerAdd(GameAnswerRecord gameAnswerRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionId", gameAnswerRecord.questionId());
        param.addValue("userAnswer", gameAnswerRecord.userAnswer());
        param.addValue("dateId", gameAnswerRecord.dateId());
        var i = jdbcTemplate.update("insert into user_game_detail (question_id, user_answer, date_id) \n" +
                        "values (:questionId, :userAnswer, :dateId)", param);
        return i;
    }

    @Override
    public int gameTimeAdd(int id, int resultTime) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        param.addValue("resultTime", resultTime);
        var i = jdbcTemplate.update("update user_game set game_score = :resultTime where id = :id", param);
        return i;
    }

    @Override
    public GameScoreRecord gameScoreSelect(int userGameId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userGameId", userGameId);
        var id = jdbcTemplate.query("select game_score from user_game where id = :userGameId"
                , param,new DataClassRowMapper<>(GameScoreRecord.class));
        return id.isEmpty() ? null : id.get(0);
    }
}
