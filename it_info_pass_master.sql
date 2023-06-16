CREATE DATABASE it_info_pass_master;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  login_id VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL
);

CREATE TABLE category (
  id SERIAL PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL
);

CREATE TABLE questions (
  id SERIAL PRIMARY KEY,
  question_name VARCHAR(255) NOT NULL,
  question_text TEXT NOT NULL,
  answer_text TEXT,
  category_id INT NOT NULL
);

CREATE TABLE choice (
  id SERIAL PRIMARY KEY,
  choice_text VARCHAR(255) NOT NULL,
  answer BOOLEAN NOT NULL,
  question_id INT NOT NULL
);

CREATE TABLE age (
  id SERIAL PRIMARY KEY,
  age VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE question_age (
  id SERIAL PRIMARY KEY,
  age_id INT NOT NULL,
  question_id INT NOT NULL
);

CREATE TABLE user_check (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  question_age_id INT NOT NULL,
  perfect_check INT NOT NULL,
  look_check INT NOT NULL
);

CREATE TABLE inquiry (
  id SERIAL PRIMARY KEY,
  inquiry_text TEXT NOT NULL,
  user_id INT NOT NULL,
  inquiry_answer TEXT,
  check_inquiry INT ,
  read_inquiry INT
);

CREATE TABLE game_age (
  id SERIAL PRIMARY KEY,
  age_id INT NOT NULL,
  question_id INT NOT NULL
);

CREATE TABLE user_game_detail (
  id SERIAL PRIMARY KEY,
  question_id INT NOT NULL,
  user_answer INT,
  date_id INT NOT NULL
);

CREATE TABLE user_game (
  id SERIAL PRIMARY KEY,
  game_date timestamp NOT NULL,
  user_id INT NOT NULL,
  age_id INT NOT NULL,
  game_score INT
);

CREATE TABLE category_select (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  category_id INT NOT NULL,
  age_id INT NOT NULL,
  category_select INT NOT NULL
);
INSERT INTO age (age)
VALUES
    ('2023'),
    ('2024'),
    ('2025'),
    ('2026'),
    ('2027');

INSERT INTO category (category_name)
VALUES ('データベース'), ('システム構成要素'), ('情報処理'), ('ネットワーク'), ('マネジメント'), ('セキュリティ');

INSERT INTO users (login_id, password, name, role)
VALUES
    ('id', 'pass', '太郎', 'user'),
    ('id1', 'pass1', '花子', 'user'),
    ('id2', 'pass2', '次郎', 'user'),
    ('admin', 'admin', '管理者', 'admin');

INSERT INTO questions (question_name, question_text, answer_text, category_id)
VALUES
    ('データベースの回復処理', 'データベースが格納されている記憶媒体に故障が発生した場合，バックアップファイルとログを用いてデータベースを回復する操作はどれか。', 'チェックポイントダンプ', 1),
    ('データ操作1', 'SQLの構文として，正しいものはどれか', 'SELECT 注文日, AVG(数量) FROM 注文明細 GROUP BY 注文日', 1),
    ('フォールトトレラントシステムの説明', 'フォールトトレラントシステムの説明として，適切なものはどれか。', 'システムが部分的に故障しても，システム全体としては必要な機能を維持するシステム', 2),
    ('RAIDの分類', 'RAIDの分類において，ミラーリングを用いることで信頼性を高め，障害発生時には冗長ディスクを用いてデータ復元を行う方式はどれか。', 'RAID1', 2),
    ('OSI基本参照モデル、ネットワーク層の説明', 'OSI基本参照モデルにおけるネットワーク層の説明として，適切なものはどれか。', 'エンドシステム間のデータ伝送を実現するために，ルーティングや中継などを行う。', 4);

INSERT INTO choice (choice_text, answer, question_id)
VALUES
    ('チェックポイントダンプ', true, 1),
    ('コミット', false, 1),
    ('ロールフォワード', false, 1),
    ('リカバリ', false, 1),
    ('SELECT 注文日, AVG(数量) FROM 注文明細', true, 2),
    ('SELECT 注文日, AVG(数量) FROM 注文明細', false, 2),
    ('SELECT 注文日, AVG(SUM(数量)) FROM 注文明細 GROUP BY 注文日', false, 2),
    ('SELECT 注文日 FROM 注文明細 WHERE SUM(数量) > 1000 GROUP BY 注文日', false, 2),
    ('システムが部分的に故障しても，システム全体としては必要な機能を維持するシステム', true, 3),
    ('地域的な災害などの発生に備えて，遠隔地に予備を用意しておくシステム', false, 3),
    ('複数のプロセッサがネットワークを介して接続され，資源を共有するシステム', false, 3),
    ('複数のプロセッサで一つのトランザクションを並行して処理し，結果を照合するシステム', false, 3),
    ('RAID1', true, 4),
    ('RAID2', false, 4),
    ('RAID3', false, 4),
    ('RAID4', false, 4),
    ('エンドシステム間のデータ伝送を実現するために，ルーティングや中継などを行う。', true, 5),
    ('各層のうち，最も利用者に近い部分であり，ファイル転送や電子メールなどの機能が実現されている。', false, 5),
    ('物理的な通信媒体の特性の差を吸収し，上位の層に透過的な伝送路を提供する。', false, 5),
    ('隣接ノード間の伝送制御手順(誤り検出，再送制御など)を提供する。', false, 5);
INSERT INTO question_age (age_id, question_id)
VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5);

INSERT INTO user_check (user_id, question_age_id, perfect_check, look_check)
VALUES
  (1, 1, 1, 1),
  (2, 1, 1, 1),
  (3, 1, 1, 1);

INSERT INTO inquiry (inquiry_text, user_id, inquiry_answer, check_inquiry, read_inquiry)
VALUES
  ('2023年の問題1にバグがあります', 1, null, 0, 0);

INSERT INTO game_age (age_id, question_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5);

INSERT INTO user_game (game_date, user_id, age_id, game_score)
VALUES
    (now(), 1, 1, 60),
    (now(), 2, 1, 61),
    (now(), 3, 1, 62);

INSERT INTO user_game_detail (question_id, user_answer, date_id)
VALUES
    (1, 1, 1),
    (2, 1, 1),
    (3, 1, 1),
    (4, 1, 1),
    (5, 1, 1),
    (1, 1, 2),
    (2, 1, 2),
    (3, 1, 2),
    (4, 1, 2),
    (5, 1, 2),
    (1, 1, 3),
    (2, 1, 3),
    (3, 1, 3),
    (4, 1, 3),
    (5, 1, 3);

INSERT INTO category_select (user_id, category_id, age_id, category_select)
VALUES
  (1, 1, 1, 1),
  (1, 2, 1, 1),
  (1, 3, 1, 0),
  (1, 4, 1, 1),
  (1, 5, 1, 0),
  (1, 6, 1, 0),
  (2, 1, 1, 1),
  (2, 2, 1, 1),
  (2, 3, 1, 0),
  (2, 4, 1, 1),
  (2, 5, 1, 0),
  (2, 6, 1, 0),
  (3, 1, 1, 1),
  (3, 2, 1, 1),
  (3, 3, 1, 0),
  (3, 4, 1, 1),
  (3, 5, 1, 0),
  (3, 6, 1, 0),
  (4, 1, 1, 1),
  (4, 2, 1, 1),
  (4, 3, 1, 0),
  (4, 4, 1, 1),
  (4, 5, 1, 0),
  (4, 6, 1, 0),
  (4, 6, 1, 0);
