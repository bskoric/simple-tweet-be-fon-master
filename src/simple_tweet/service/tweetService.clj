(ns simple-tweet.service.tweetService
  (:require [simple-tweet.dao.tweetDao :as tweet-dao]))

(defn get-tweets [] (tweet-dao/find-tweets []))
