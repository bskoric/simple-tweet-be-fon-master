(ns simple-tweet.api.userAPI
  (:require
    [clojure.data.json :as json]
    [clojure.walk :as walk]
    [ring.util.response :as response-utils]
    [ring.util.request :as request-utils]
    [simple-tweet.dao.userDao :as user-dao]
    [simple-tweet.service.userService :as user-service])
  )

(defn login [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (->
          (response-utils/response (json/write-str (user-service/check-username-and-password (get params :username "")
                                                                                             (get params :password "")
                                                                                             )))
          (response-utils/header "Content-Type" "application/json")
          )
        (catch Exception e
          (response-utils/status (response-utils/response (str "Error, can't find user.\n" (.toString e))) 400)
          (println (.toString e)))))))


(def get-all-users (->
                     (response-utils/response (json/write-str user-dao/users))
                     (response-utils/header "Content-Type" "application/json")
                     )
  )

(defn get-user "Gets user by username" [params]
  (let [inputs (walk/keywordize-keys params)]
    (try
      (response-utils/response (json/write-str (user-dao/find-user (get inputs :username ""))))
      (catch Exception e
        (response-utils/status (response-utils/response (str "Error: " (.toString e))) 400)))))


(defn get-users "Gets all users except with id" [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (->
          (response-utils/response (json/write-str (user-dao/find-users-except-id (get params :userID ""))))
          (response-utils/header "Content-Type" "application/json")
          )
        (catch Exception e (response-utils/status
                             (response-utils/response (str "Error, can't find user.\n" (.toString e))) 400))))))

(defn get-friends [req]
  (let [body (request-utils/body-string req)]
    (let [params (json/read-str body :key-fn keyword)]
      (try
        (->
          (response-utils/response (json/write-str (user-dao/find-friends (get params :userID ""))))
          (response-utils/header "Content-Type" "application/json")
          )
        (catch Exception e
          (response-utils/status (response-utils/response (str "Error, can't find user.\n" (.toString e))) 400)
          (println (.toString e)))))))
