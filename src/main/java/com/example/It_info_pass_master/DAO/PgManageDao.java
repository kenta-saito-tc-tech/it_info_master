package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgManageDao implements ManageDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public QuestionRecord questionInsert(QuestionRecord questionRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionName", questionRecord.questionName());
        param.addValue("questionText",questionRecord.questionText());
        param.addValue("answerText", questionRecord.answerText());
        param.addValue("categoryId", Integer.parseInt(questionRecord.categoryId()));
        var list = jdbcTemplate.query("insert into questions(question_name, question_text, answer_text, category_id) \n" +
                        "values (:questionName, :questionText, :answerText, :categoryId) \n" +
                        "RETURNING *"
                , param,new DataClassRowMapper<>(QuestionRecord.class));
        for (var i : list) {
            MapSqlParameterSource param2 = new MapSqlParameterSource();
            param2.addValue("questionId", i.id());
            jdbcTemplate.update("insert into question_age(age_id, question_id) values (0, :questionId)", param2);
        }
        System.out.println(list);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int choiceInsert(List<ChoiceRecord> choiceRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        var list = 0;
        for (var count : choiceRecord) {
            param.addValue("choiceText", count.choiceText());
            param.addValue("answer", count.answer());
            param.addValue("questionId",count.questionId());
             list = jdbcTemplate.update("insert into choice(choice_text, answer, question_id) \n" +
                            "values (:choiceText, :answer, :questionId)"
                    , param);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<UserAgeRecord> adminAllAgeSelect() {
        var ageList = jdbcTemplate.query("SELECT * FROM age", new DataClassRowMapper<>(UserAgeRecord.class));

        return ageList;
    }

    @Override
    public List<QuestionRecord> adminAllQuestionSelect() {
        var list = jdbcTemplate.query("select * from questions", new DataClassRowMapper<>(QuestionRecord.class));
        return list;
    }

    @Override
    public List<AdminQuestionRecord> adminCheckAge(Integer age) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("age", age);
        var list = jdbcTemplate.query("SELECT * FROM question_age WHERE age_id = :age",param, new DataClassRowMapper<>(AdminQuestionRecord.class));
        return list;
    }

    @Override
    public int adminSetQuestion(List<AdminQuestionRecord> setQuestion) {
        int ok = 0;
        for (var i : setQuestion) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("questionId", i.questionId());
            param.addValue("ageId", i.ageId());
            jdbcTemplate.update("insert into question_age(age_id, question_id) values (:ageId, :questionId)", param);
        }
        return ok;
    }

    @Override
    public List<AdminQuestionRecord> adminCheckGameAge(Integer age) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("age", age);
        var list = jdbcTemplate.query("SELECT * FROM game_age WHERE age_id = :age",param, new DataClassRowMapper<>(AdminQuestionRecord.class));
        return list;
    }

    @Override
    public int adminSetGameQuestion(List<AdminQuestionRecord> setQuestion) {
        int ok = 0;
        for (var i : setQuestion) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("questionId", i.questionId());
            param.addValue("ageId", i.ageId());
            jdbcTemplate.update("insert into game_age(age_id, question_id) values (:ageId, :questionId)", param);
        }
        return ok;
    }

    @Override
    public Integer adminAddAge(Integer age) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("age", age);
        return jdbcTemplate.update("insert into age(age) values (:age)", param);
    }

    @Override
    public int adminAddQuestion(List<AdminQuestionRecord> setQuestion) {
        int ok = 0;
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", setQuestion.get(0).ageId());

        // データの削除
        jdbcTemplate.update("DELETE FROM question_age WHERE age_id = :ageId", param);
        System.out.println(setQuestion + "------------");

        MapSqlParameterSource param2 = new MapSqlParameterSource();
        // データの挿入
        var j = 1;
        for (var i : setQuestion) {
            System.out.println(j + "回目");
            param2.addValue("questionId", i.questionId());
            param2.addValue("ageId", i.ageId());
            jdbcTemplate.update("INSERT INTO question_age (age_id, question_id) VALUES (:ageId, :questionId)", param2);
            System.out.println(j + "回目成功");
            j= j +1;
        }

        return ok;
    }

    @Override
    public List<QuestionIdRecord> adminCheckImpossible(int ageId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        var list = jdbcTemplate.query("SELECT question_id FROM game_age WHERE age_id = :ageId", param, new DataClassRowMapper<>(QuestionIdRecord.class));
        return list;
    }


}
