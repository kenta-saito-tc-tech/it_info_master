package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
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
                "AND category_id = :categoryId " +
                "ORDER BY id";
        List<QuestionRecord> result = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
        return result;
    }

    @Override
    public Integer selectPerfectCheck(int userId, int ageId, int questionId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userId", userId);
        param.addValue("ageId", ageId);
        param.addValue("questionId", questionId);

        String sql = "select perfect_check " +
                "from user_check " +
                "where question_age_id = (select id " +
                "from question_age " +
                "where age_id = :ageId " +
                "AND question_id = :questionId) " +
                "AND user_id = :userId ";

        var perfectCheck = jdbcTemplate.queryForObject(sql, param, Integer.class);

        return perfectCheck;
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
    public int checkComplete(int id, int userId, int ageId) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        param.addValue("questionId", id);
        param.addValue("userId", userId);
        param.addValue("ageId", ageId);

        return jdbcTemplate.update("UPDATE user_check " +
                "SET perfect_check = 2 " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId " +
                "AND age_id = :ageId) " +
                "AND user_id = :userId", param);
    }

    @Override
    public int checkNotComplete(int id, int userId, int ageId) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        param.addValue("questionId", id);
        param.addValue("userId", userId);
        param.addValue("ageId", ageId);

        return jdbcTemplate.update("UPDATE user_check " +
                "SET perfect_check = 1 " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId " +
                "AND age_id = :ageId) " +
                "AND user_id = :userId", param);
    }

    @Override
    public int checkCompleteCheck(int id, int userId, int ageId) {
        String sql = "SELECT perfect_check " +
                "FROM user_check " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId " +
                "AND age_id = :ageId) " +
                "AND user_id = :userId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionId", id);
        param.addValue("userId", userId);
        param.addValue("ageId", ageId);


        var check = jdbcTemplate.queryForObject(sql, param, Integer.class);
        return check;
    }

    //user_checkテーブルを確認＆作成するメソッド
    public int findCheckUser(int userId, int questionId, int ageId) {
        System.out.println("finCheckUser");

        //レコードクラスの有無をチェック
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userId", userId);
        param.addValue("questionId", questionId);
        param.addValue("ageId", ageId);
        System.out.println("dao/ageId:"+ageId);

        var checkUser = jdbcTemplate.query("SELECT * FROM user_check " +
                "WHERE user_id = :userId " +
                "AND question_age_id IN (" +
                "    SELECT id FROM question_age " +
                "    WHERE age_id = :ageId " +
                "    AND question_id = :questionId" +
                ");", param, new DataClassRowMapper<>(UserCheckRecord.class));
        int resultSet = checkUser.isEmpty() ? 0 : 1;

        //insertするためのif文（開始）
        if (resultSet == 1) {
            return resultSet;

        } else {
            //MapSqlParameterSource param1 = new MapSqlParameterSource();

            var questionAgeId = jdbcTemplate.queryForObject("SELECT id FROM question_age WHERE question_id = :questionId AND age_id = :ageId", param, Integer.class);

            System.out.print("DAO　クエスチョンAgeId:" + questionAgeId);

//            return questionId;


            //insert1するSQL文
            MapSqlParameterSource param2 = new MapSqlParameterSource();
            param2.addValue("userId", userId);
            param2.addValue("questionId", questionId);
            param2.addValue("questionAgeId", questionAgeId);

            var insertRecord = jdbcTemplate.update("INSERT INTO user_check (user_id, question_age_id, perfect_check, look_check) " +
                    "VALUES (:userId, :questionAgeId, 1 , 1)", param2);

            System.out.print("インサートした件数"+insertRecord);
        }


        System.out.print("DAOから確認 レコードの有無：" + resultSet);
        return resultSet;
    }

    @Override
    public String findAge(int ageId) {
        String sql = "Select age " +
                "FROM age " +
                "WHERE id = :ageId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);

        var age = jdbcTemplate.queryForObject(sql, param, String.class);
        return age;
    }

    @Override
    public int checkLookCheck(int id, int userId, int ageId) {
        String sql = "SELECT look_check " +
                "FROM user_check " +
                "WHERE question_age_id = (SELECT id " +
                "FROM question_age WHERE question_id = :questionId " +
                "AND age_id = :ageId) " +
                "AND user_id = :userId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionId", id);
        param.addValue("userId", userId);
        param.addValue("ageId", ageId);


        var check = jdbcTemplate.queryForObject(sql, param, Integer.class);
        return check;
    }

    @Override
    public int findQuestionId(String title) {
        String sql = "Select id " +
                "FROM questions " +
                "WHERE question_name = :questionName " +
                "limit 1";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionName", title);

        var age = jdbcTemplate.queryForObject(sql, param, Integer.class);
        return age;
    }

    @Override
    public String findCategory(int categoryId) {
        String sql = "SELECT category_name " +
                "FROM category " +
                "WHERE id = :categoryId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("categoryId", categoryId);
        var category = jdbcTemplate.queryForObject(sql, param, String.class);
        return category;
    }

}
