(ns simple-tweet.dao.tweetDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db-init :as pool]
            [taoensso.timbre :as log
             :refer [log  trace  debug  info  warn  error  fatal  report]]))

(def find-tweets (db/query pool/my-pool
                      ["select * from tweet t join user u on t.user = u.user_id"]))

(defn find-tweet-by-id [id] (db/query pool/my-pool
                           ["select * from tweet t join user u on t.user = u.user_id where tweet_id = ?" id]))

(defn find-tweets-by-user "Gets user by username" [username]
  (db/query pool/my-pool
            ["select * from tweet t join user u on t.user = u.user_id WHERE username = ?" username]))

(defn find-friends-tweets "Find all friends tweets and my" [user-id]
  (db/query pool/my-pool ["SELECT tweet_id, title, post, date, u.user_id, first_name, last_name, email
  FROM `tweet` t JOIN user u ON t.user = u.user_id
  WHERE user in (SELECT user_id from friendship where friend_id = ?) or user = ?
  ORDER by date DESC" user-id user-id])
  )

(defn insert-tweet [title post user]
  (db/insert! pool/my-pool "tweet" {:title title :post post :user user})
  (log/info "Inserting tweet " title)
  )

(defn update-tweet [id title post]
  (db/update!
    pool/my-pool "tweet" {:title title :post post} ["tweet_id=?" id])
  (log/info "Updating tweet " id)
  (format "Tweet successfully updated")
  )

(defn delete-tweet [id]
  (db/delete!
    pool/my-pool "tweet" ["tweet_id=?" id])
  (log/info "Deleting tweet " id)
  (format "Deleting tweet [%s]" id)
  )