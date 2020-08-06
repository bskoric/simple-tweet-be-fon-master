(ns simple-tweet.db-init
  (:require [jdbc.pool.c3p0 :as pool])
  )

(def mysql_db {
               :classname         "com.mysql.cj.jdbc.Driver"
               :subprotocol       "mysql"
               :subname           "//localhost:3306/simple_tweet_alati?useUnicode=true&serverTimezone=UTC"
               :user              "root"
               :password          "admin"
               :min-pool-size     3
               :initial-pool-size 3})

(def my-pool (pool/make-datasource-spec mysql_db))
