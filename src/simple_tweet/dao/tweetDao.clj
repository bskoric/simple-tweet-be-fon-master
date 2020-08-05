(ns simple-tweet.dao.tweetDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db.db-init :as pool]))

(def tweets (db/query pool/my-pool
                      ["select * from tweet"]))