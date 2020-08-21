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
            ["SELECT user_id, first_name, last_name, username, email FROM user WHERE username = ? AND password = ?" username password]))

(defn find-users-except-id "Gets all users except with provide id" [user-id]
  (db/query pool/my-pool
            ["SELECT * FROM `user` WHERE not user_id = ?" user-id]))

(defn find-friends [user-id]
  (db/query pool/my-pool
            ["SELECT friend_id, date, first_name, last_name, username, email
            FROM friendship f join user u on f.friend_id = u.user_id WHERE f.user_id = ?" user-id]))

(defn find-non-friends [user-id]
  (db/query pool/my-pool
            ["SELECT user_id, first_name, last_name, username, email
            FROM user
            WHERE user_id not in (SELECT friend_id
            FROM friendship f join user u on f.friend_id = u.user_id WHERE f.user_id = ?) and user_id != ?" user-id user-id]))

(defn find-password "Find encrypt password for username" [username]
  (:password (first (db/query pool/my-pool
                          ["SELECT password FROM user WHERE username = ?" username] ))))

(defn insert-user [firstname lastname username password email]
  (db/insert! pool/my-pool "user" {:first_name firstname :last_name lastname :username username :password password :email email})
  (println (format "Inserting user %s %s %s" firstname lastname username))
  )