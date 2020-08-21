(ns simple-tweet.api.tweetAPI
  (:require
    [clojure.data.json :as json]
    [ring.util.request :as request-utils]
    [clojure.walk :as walk]
    [ring.util.response :as response-utils]
    [simple-tweet.dao.tweetDao :as tweet-dao]))

(extend-type java.sql.Timestamp
  json/JSONWriter
  (-write [date out]
    (json/-write (str date) out)))

(def get-all-tweets (->
                      (response-utils/response (json/write-str tweet-dao/find-tweets))
                      (response-utils/header "Content-Type" "application/json")
                      )
  )

(defn get-tweet-by-id [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (->
          (response-utils/response (json/write-str (tweet-dao/find-tweet-by-id (get params :tweet_id ""))))
          (response-utils/header "Content-Type" "application/json")
          )
        (catch Exception e (response-utils/status (response-utils/response "Error, can't find tweet. Try again.") 400))))))

(defn get-all-tweets-by-user "Gets all tweets by user" [params]
  (let [inputs (walk/keywordize-keys params)]
    (try
      (response-utils/response (json/write-str (tweet-dao/find-tweets-by-user (get inputs :user ""))))
      (catch Exception e (response-utils/status (response-utils/response "Error") 400)))))


(defn get-all-friends-tweets "Gets all friends tweets and my" [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (->
          (response-utils/response (json/write-str (tweet-dao/find-friends-tweets (get params :userID ""))))
          (response-utils/header "Content-Type" "application/json")
          )
        (catch Exception e (response-utils/status (response-utils/response "Error, can't find tweets. Try again.") 400))))))


(defn insert-tweet [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (response-utils/response (json/write-str (tweet-dao/insert-tweet (get params :title "")
                                                                         (get params :post "")
                                                                         (get params :user ""))))
        (catch Exception e (response-utils/status (response-utils/response "Error, can't insert tweet. Try again.") 400)
                           (println (.toString e)))))))

(defn update-tweet [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (response-utils/response (json/write-str (tweet-dao/update-tweet (get params :id "")
                                                                         (get params :title "")
                                                                         (get params :post ""))))
        (catch Exception e (response-utils/status (response-utils/response "Error, can't update tweet. Try again.") 400)
                           (println (.toString e)))))))

(defn delete-tweet [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (response-utils/response (json/write-str (tweet-dao/delete-tweet (get params :tweet_id ""))))
        (catch Exception e (response-utils/status
                             (response-utils/response "Error, can't update tweet. Try again.") 400)
                           (println (.toString e)))))))