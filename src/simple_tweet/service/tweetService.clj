(ns simple-tweet.service.tweetService
  (:require [simple-tweet.dao.tweetDao :as tweet-dao]))

(defn get-tweets [] tweet-dao/find-tweets)

(defn get-tweets-by-user "Get all tweets by user" [user-id] (tweet-dao/find-tweets-by-user user-id))

(defn insert-tweet [title post date user] (tweet-dao/insert-tweet title post date user))
