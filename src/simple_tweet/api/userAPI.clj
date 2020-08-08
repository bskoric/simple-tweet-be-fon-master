(ns simple-tweet.api.userAPI
  (:require
    [clojure.data.json :as json]
    [clojure.walk :as walk]
    [ring.util.response :as response-utils]
    [simple-tweet.service.userService :as user-service]))

(defn get-user "Gets user" [params]
  (let [inputs (walk/keywordize-keys params)]
    (try
      (response-utils/response (json/write-str (user-service/get-user (get inputs :username ""))))
      (catch  Exception e (response-utils/status (response-utils/response "Error") 400)))))
