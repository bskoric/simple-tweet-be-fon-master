(ns simple-tweet.service.userService
  (:require [simple-tweet.dao.userDao :as user-dao]))

(defn get-user "Gets user by username" [username]
  (user-dao/find-user username))
