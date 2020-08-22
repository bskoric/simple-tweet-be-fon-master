(ns simple-tweet.dao.tweetDao
  (:require [clojure.java.jdbc :as db]
            [simple-tweet.db-init :as pool]
            [taoensso.timbre :as log
             :refer [log trace debug info warn error fatal report]]))

(def find-tweets (db/query pool/my-pool
                           ["select * from tweet t join user u on t.user = u.user_id"]))

(defn find-tweet-by-id [id] (db/query pool/my-pool
                                      ["select * from tweet t join user u on t.user = u.user_id where tweet_id = ?" id]))

(defn find-tweets-by-user "Gets tweets by username" [username]
  (db/query pool/my-pool
            ["select * from tweet t join user u on t.user = u.user_id WHERE username = ? order by date DESC" username]))

(defn find-friends-tweets "Find all friends tweets and my" [user-id]
  (db/query pool/my-pool ["SELECT tweet_id, title, post, date, u.user_id, first_name, last_name, email
  FROM `tweet` t JOIN user u ON t.user = u.user_id
  WHERE user in (SELECT friend_id from friendship where user_id = ?) or user = ?
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

(defn insert-like [tweet_id user_id]
  (db/insert! pool/my-pool "likes" {:tweet_id tweet_id :user_id user_id})
  (str "Like successfully added")
  )

(defn check-like [tweet_id user_id]
(first (db/query pool/my-pool ["SELECT count(*) as 'like' FROM likes WHERE tweet_id = ? AND user_id = ?" tweet_id user_id]))
  )

(defn delete-like [tweet_id user_id]
  (db/delete!
    pool/my-pool "likes" ["tweet_id = ? AND user_id = ?" tweet_id user_id])
  (format "Like deleted")
  )

(defn find-number-of-likes "Gets number of likes for tweet" [tweet_id]
  (first (db/query pool/my-pool
                   ["SELECT count(*) as 'likes' FROM likes WHERE tweet_id = ?" tweet_id])))
