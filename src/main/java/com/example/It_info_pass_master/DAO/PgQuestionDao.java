package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PgQuestionDao implements QuestionDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<UserAgeRecord> ageFindAll(){

        var ageList = jdbcTemplate.query("SELECT * FROM age",new DataClassRowMapper<>(UserAgeRecord.class));

        for(var age : ageList){
            System.out.print(age);
        }
        return ageList;
    }

    @Override
    public List<QuestionRecord> selectQuestion(int ageId, int categoryId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        param.addValue("categoryId", categoryId);

        String query = "select * " +
                "from questions " +
                "where id IN (select question_id " +
                "from question_age " +
                "where age_id = :ageId) " +
                "AND category_id = :categoryId";
        List<QuestionRecord> result = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
        return result;
    }

    @Override
    public QuestionRecord findQuestion(int id){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param, new DataClassRowMapper<>(QuestionRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChoiceRecord> findChoices(int id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        String query = "SELECT *" +
                "FROM choice " +
                "WHERE question_id = :id";
        List<ChoiceRecord> result = jdbcTemplate.query(query, param, new DataClassRowMapper<>(ChoiceRecord.class));
        return result;
    }

    @Override
    public String findAnswer(int id) {
        String sql = "SELECT choice_text " +
                "FROM choice " +
                "WHERE question_id = :question_id " +
                "AND answer = true";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("question_id", id);

        var answer = jdbcTemplate.queryForObject(sql, param, String.class);
        return answer;
    }

    @Override
    public int checkComplete(int id, int userId) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        param.addValue("questionId", id);
        param.addValue("userId", userId);

        return jdbcTemplate.update("UPDATE user_check " +
                "SET perfect_check = 2 " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId) " +
                "AND user_id = :userId", param);
    }

    @Override
    public int checkNotComplete(int id, int userId) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        param.addValue("questionId", id);
        param.addValue("userId", userId);

        return jdbcTemplate.update("UPDATE user_check " +
                "SET perfect_check = 1 " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId) " +
                "AND user_id = :userId", param);
    }

    @Override
    public int checkCompleteCheck(int id, int userId) {
        String sql = "SELECT perfect_check " +
                "FROM user_check " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId) " +
                "AND user_id = :userId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionId", id);
        param.addValue("userId", userId);

        var answer = jdbcTemplate.queryForObject(sql, param, Integer.class);
        return answer;
    }





}
