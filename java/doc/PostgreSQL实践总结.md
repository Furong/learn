## PostgreSQL实践总结

### 1. 查看执行计划

t_socres表中有5条数据；

t_user表中有1000条数据；

```sql
CREATE TABLE "public"."t_user" (
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "gender" int2 NOT NULL,
  "birth_date" date,
  "id" int4 NOT NULL,
  "student_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_user"."name" IS '姓名';
COMMENT ON COLUMN "public"."t_user"."gender" IS '性别';
COMMENT ON COLUMN "public"."t_user"."birth_date" IS '出生日期';
COMMENT ON COLUMN "public"."t_user"."student_id" IS '学号';

-- ----------------------------
-- Indexes structure for table t_user
-- ----------------------------
CREATE INDEX "idx_t_user_birth" ON "public"."t_user" USING btree (
  "birth_date" "pg_catalog"."date_ops" ASC NULLS LAST
);

CREATE TABLE "public"."t_scores" (
  "id" int4 NOT NULL,
  "name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "course" varchar(128) COLLATE "pg_catalog"."default",
  "score" int4,
  "student_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_scores"."name" IS '姓名';
COMMENT ON COLUMN "public"."t_scores"."course" IS '课程';
COMMENT ON COLUMN "public"."t_scores"."score" IS '得分';
COMMENT ON COLUMN "public"."t_scores"."student_id" IS '学号';

-- ----------------------------
-- Indexes structure for table t_scores
-- ----------------------------
CREATE INDEX "idx_t_scores_score" ON "public"."t_scores" USING btree (
  "score" "pg_catalog"."int4_ops" ASC NULLS LAST
);
```



```sql
db=# explain analyse select * from t_scores where score < 90;
                                             QUERY PLAN
-----------------------------------------------------------------------------------------------------
 Seq Scan on t_scores  (cost=0.00..1.13 rows=3 width=1072) (actual time=0.025..0.026 rows=5 loops=1)
   Filter: (score < 90)
   Rows Removed by Filter: 5
 Planning time: 0.134 ms
 Execution time: 0.054 ms
(5 行记录)


db=# explain analyse select * from t_user;
                                               QUERY PLAN
---------------------------------------------------------------------------------------------------------
 Seq Scan on t_user  (cost=0.00..156.99 rows=8999 width=23) (actual time=0.019..0.594 rows=8999 loops=1)
 Planning time: 0.823 ms
 Execution time: 0.717 ms
(3 行记录)


db=# explain analyse select * from t_user where birth_date < '2010-01-01';
                                                           QUERY PLAN
--------------------------------------------------------------------------------------------------------------------------------
 Bitmap Heap Scan on t_user  (cost=57.73..152.87 rows=2251 width=23) (actual time=0.120..0.332 rows=2249 loops=1)
   Recheck Cond: (birth_date < '2010-01-01'::date)
   Heap Blocks: exact=67
   ->  Bitmap Index Scan on idx_t_user_birth  (cost=0.00..57.17 rows=2251 width=0) (actual time=0.106..0.106 rows=2249 loops=1)
         Index Cond: (birth_date < '2010-01-01'::date)
 Planning time: 0.195 ms
 Execution time: 0.395 ms
(7 行记录)


db=# explain analyse select * from t_user t1 left join t_scores t2 on t1.student_id= t2.student_id where t1.birth_date < '2010-01-01';
                                                              QUERY PLAN
--------------------------------------------------------------------------------------------------------------------------------------
 Hash Left Join  (cost=58.96..162.56 rows=2251 width=1095) (actual time=0.155..0.690 rows=2254 loops=1)
   Hash Cond: ((t1.student_id)::text = (t2.student_id)::text)
   ->  Bitmap Heap Scan on t_user t1  (cost=57.73..152.87 rows=2251 width=23) (actual time=0.103..0.349 rows=2249 loops=1)
         Recheck Cond: (birth_date < '2010-01-01'::date)
         Heap Blocks: exact=67
         ->  Bitmap Index Scan on idx_t_user_birth  (cost=0.00..57.17 rows=2251 width=0) (actual time=0.091..0.091 rows=2249 loops=1)
               Index Cond: (birth_date < '2010-01-01'::date)
   ->  Hash  (cost=1.10..1.10 rows=10 width=1072) (actual time=0.020..0.020 rows=10 loops=1)
         Buckets: 1024  Batches: 1  Memory Usage: 9kB
         ->  Seq Scan on t_scores t2  (cost=0.00..1.10 rows=10 width=1072) (actual time=0.011..0.012 rows=10 loops=1)
 Planning time: 0.253 ms
 Execution time: 0.777 ms
```

