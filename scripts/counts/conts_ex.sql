set search_path = "social_net_otus";

SELECT count(*) FROM social_net_otus.sno_user_register;

EXPLAIN ANALYSE
SELECT
    (SELECT 0 FROM social_net_otus.sno_posts);

-- SELECT reltuples ,regclass("") relname, relpages FROM pg_class ;

select pg_table_size(regclass('sno_posts'));
-- select pg_table_(regclass('sno_posts'));


EXPLAIN ANALYSE
SELECT n_live_tup
FROM pg_stat_user_tables
WHERE relname = 'sno_posts';


SELECT (SELECT n_live_tup
        FROM pg_stat_user_tables
        WHERE relname = 'sno_posts') = (SELECT count(*) FROM social_net_otus.sno_posts);

EXPLAIN ANALYSE
SELECT count(1) FROM social_net_otus.sno_posts;

EXPLAIN ANALYSE
SELECT count(id) FROM social_net_otus.sno_posts;

EXPLAIN ANALYSE
SELECT count(*) FROM social_net_otus.sno_posts;

ANALYZE social_net_otus.sno_posts;

SELECT reltuples::bigint AS row_count
FROM pg_class
WHERE oid = 'social_net_otus.sno_posts'::regclass;

-- Сначала обновляем статистику
ANALYZE social_net_otus.sno_posts;

-- Затем получаем точное количество строк из системного представления
SELECT n_live_tup
FROM pg_stat_user_tables
WHERE relname = 'sno_posts';

SELECT schemaname, relname, n_live_tup
FROM pg_stat_user_tables
ORDER BY n_live_tup DESC;