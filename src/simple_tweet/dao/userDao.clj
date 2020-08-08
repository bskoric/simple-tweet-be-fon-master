(ns simple-tweet.dao.userDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db-init :as pool]))

(def users (db/query pool/my-pool
                      ["select * from user"]))

(defn find-user "Gets user by username" [username]
  (db/query pool/my-pool
                     ["SELECT * FROM user WHERE username = ?" username]))

