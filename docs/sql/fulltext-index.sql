-- assignment DB에 post 테이블이 생성된 후 최초 1회 실행
-- MySQL 서버 설정: ngram_token_size=2

USE assignment;

CREATE FULLTEXT INDEX idx_post_title_ngram
ON post (title) WITH PARSER ngram;