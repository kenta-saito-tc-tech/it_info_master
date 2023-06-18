package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.GameQuestionRecord;
import com.example.It_info_pass_master.Entity.GameSelectRecord;
import com.example.It_info_pass_master.Entity.ageidRecord;
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
        var id = jdbcTemplate.query("select E.age, G.question_id, Ca.category_name, Q.question_name, Q.question_text,A.choice_text, B.choice_text, C.choice_text, D.choice_text from game_age as G \n" +
                        "join age as E on G.age_id = E.id \n" +
                        "join questions as Q on G.question_id = Q.id \n" +
                        "join category as Ca on Q.category_id = Ca.id \n" +
                        "JOIN choice AS A ON G.question_id = A.question_id \n" +
                        "JOIN choice AS B ON A.id = B.id - 1\n" +
                        "JOIN choice AS C ON A.id = C.id - 2\n" +
                        "JOIN choice AS D ON A.id = D.id - 3\n" +
                        "where G.age_id = (select age_id from user_game where id = :ageId) \n" +
                        "and G.question_id = (select question_id from game_age offset :i limit 1)\n" +
                        "limit 1;"
                , param, new DataClassRowMapper<>(GameQuestionRecord.class));
        return id.isEmpty() ? null : id.get(0);
    }

    public GameSelectRecord gameChoiceSelect(int ageId, int i) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId",ageId);
        param.addValue("i",i);
        var id = jdbcTemplate.query("SELECT A.choice_text, B.choice_text, C.choice_text, D.choice_text\n" +
                        "FROM choice AS A\n" +
                        "JOIN choice AS B ON A.id = B.id - 1\n" +
                        "JOIN choice AS C ON A.id = C.id - 2\n" +
                        "JOIN choice AS D ON A.id = D.id - 3\n" +
                        "join game_age on A.question_id = game_age.question_id \n" +
                        "where game_age.age_id = (select age_id from user_game where id = :ageId) \n" +
                        "and game_age.question_id = (select question_id from game_age offset :i limit 1)\n" +
                        "limit 1;"
                , param,new DataClassRowMapper<>(GameSelectRecord.class));
        return id.isEmpty() ? null : id.get(0);
    }
}
