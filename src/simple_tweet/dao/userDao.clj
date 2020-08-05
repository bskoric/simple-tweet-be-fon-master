(ns simple-tweet.dao.userDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db.db-init :as pool]))

(def users (db/query pool/my-pool
                      ["select * from user"]))

