(ns simple-tweet.dao.userDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db-init :as pool]))

(def users (db/query pool/my-pool
                      ["select * from user"]))

(defn find-user "Gets user by username" [username]
  (db/query pool/my-pool
                     ["SELECT * FROM user WHERE username = ?" username]))

(defn find-user-by-username-password "Gets user by username and passwrd" [username password]
  (db/query pool/my-pool
            ["SELECT user_id, first_name, last_name, username, email, image FROM user WHERE username = ? AND password = ?" username password]))

(defn find-users-except-id "Gets all users except with provide id" [user-id]
  (db/query pool/my-pool
            ["SELECT * FROM `user` WHERE not user_id = ?" user-id]))

(defn find-friends [user-id]
  (db/query pool/my-pool
            ["SELECT friend_id, date, first_name, last_name, username, email
            FROM friendship f join user u on f.friend_id = u.user_id WHERE f.user_id = ?" user-id]))
