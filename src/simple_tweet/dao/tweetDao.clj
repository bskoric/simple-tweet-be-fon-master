(ns simple-tweet.dao.tweetDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db-init :as pool]))

(def find-tweets (db/query pool/my-pool
                      ["select * from tweet t join user u on t.user = u.user_id"]))

(defn find-tweets-by-user "Gets user by username" [user-id]
  (db/query pool/my-pool
            ["SELECT * FROM tweet WHERE user = ?" user-id]))

(defn find-friends-tweets "Find all friends tweets and my" [user-id]
  (db/query pool/my-pool ["SELECT tweet_id, title, post, date, u.user_id, first_name, last_name, email
  FROM `tweet` t JOIN user u ON t.user = u.user_id
  WHERE user in (SELECT user_id from friendship where friend_id = ?) or user = ?
  ORDER by date DESC" user-id user-id])
  )

(defn insert-tweet [title post date user]
  (db/insert! pool/my-pool "tweet" {:title title :post post :date date :user user})
  )