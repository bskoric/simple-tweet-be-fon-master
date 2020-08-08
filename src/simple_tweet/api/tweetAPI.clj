(ns simple-tweet.api.tweetAPI
  (:require
    [clojure.data.json :as json]
    [ring.util.request :as request-utils]
    [clojure.walk :as walk]
    [ring.util.response :as response-utils]
    [simple-tweet.service.tweetService :as tweet-service]
    [simple-tweet.dao.tweetDao :as tweet-dao]))

(extend-type java.sql.Timestamp
  json/JSONWriter
  (-write [date out]
    (json/-write (str date) out)))

(def get-all-tweets (->
                      (response-utils/response (json/write-str (tweet-service/get-tweets)))
                      (response-utils/header "Content-Type" "application/json")
                      )
  )


(defn get-all-tweets-by-user "Gets all tweets by user" [params]
  (let [inputs (walk/keywordize-keys params)]
    (try
      (response-utils/response (json/write-str (tweet-service/get-tweets-by-user (get inputs :user ""))))
      (catch Exception e (response-utils/status (response-utils/response "Error") 400)))))


(defn get-all-friends-tweets [req]
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
    (json/read-str body :key-fn keyword)
    (try
      (response-utils/response (json/write-str (tweet-service/insert-tweet (get body :title "")
                                                                           (get body :post "")
                                                                           (get body :date "")
                                                                           (get body :user ""))))
      (catch Exception e (response-utils/status (response-utils/response "Error, can't insert tweet. Try again.") 400)))))

; Simple Body Page
(defn simple-body-page [req] (response-utils/response (json/write-str (tweet-service/get-tweets))))