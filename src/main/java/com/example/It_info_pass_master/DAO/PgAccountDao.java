package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgAccountDao implements AccountDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * IDとPASSからUserを探すメソッド
     *
     * @param idPassRecord
     * @return
     */
    @Override
    public UserRecord findUser(IdPassRecord idPassRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", idPassRecord.loginId());
        param.addValue("pass", idPassRecord.password());
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :id AND password = :pass",
                param, new DataClassRowMapper<>(UserRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * IDが重複しないかチェックするメソッド
     * @param id
     * @return
     */
    @Override
    public UserRecord checkIdExist(String id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("loginId", id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :loginId",
                param, new DataClassRowMapper<>(UserRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * userの新規追加
     *
     * @param
     * @return
     */
    @Override
    public int insertUser(UserRecord userRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("loginId", userRecord.loginId());
        param.addValue("password", userRecord.password());
        param.addValue("name", userRecord.name());
        param.addValue("role", userRecord.role());
        int count = jdbcTemplate.update("INSERT INTO Users" +
                "(login_id, password, name, role)" +
                " VALUES (:loginId, :password, :name, :role)", param);
        return count == 1 ? count : null;
    }


    /**
     * ゲームスコアの上位３位の取得
     *
     * @return
     */
    @Override
    public List<RankingRecord> findTopThree(String age) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageNum", age);
        return jdbcTemplate.query("SELECT u.name, ug.game_score FROM users u JOIN user_game ug ON u.id = ug.user_id JOIN age a ON ug.age_id = a.id WHERE a.age = :ageNum ORDER BY ug.game_score ASC LIMIT 3", param,
                new DataClassRowMapper<>(RankingRecord.class));
    }

    /**
     * 年代の全取得
     *
     * @return
     */
    @Override
    public List<AgeRecord> findAgeAll() {
        return jdbcTemplate.query("SELECT id, age FROM age ORDER BY age ASC",
                new DataClassRowMapper<>(AgeRecord.class));
    }

    /**
     * ユーザーのランキング取得
     *
     * @return
     */
    public MyRankRecord findUserRank(String age, int id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageNum", age);
        param.addValue("idNum", id);
        var list = jdbcTemplate.query(
                "SELECT rank, game_score " +
                        "FROM (" +
                        "    SELECT t.user_id, t.game_score, " +
                        "           DENSE_RANK() OVER (ORDER BY t.game_score ASC) AS rank " +
                        "    FROM (" +
                        "        SELECT ug.user_id, MAX(ug.game_score) AS game_score " +
                        "        FROM user_game ug " +
                        "        WHERE ug.age_id = (SELECT id FROM age WHERE age = :ageNum)" +
                        "        GROUP BY ug.user_id" +
                        "    ) AS t" +
                        ") AS s " +
                        "WHERE s.user_id = :idNum",
                param, new DataClassRowMapper<>(MyRankRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

}
