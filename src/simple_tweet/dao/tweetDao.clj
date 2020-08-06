(ns simple-tweet.dao.tweetDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db-init :as pool]))

(def find-tweets (db/query pool/my-pool
                      ["select * from tweet"]))