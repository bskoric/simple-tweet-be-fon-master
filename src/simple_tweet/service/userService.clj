(ns simple-tweet.service.userService
  (:require [simple-tweet.dao.userDao :as user-dao]))

(defn get-users (user-dao/users))

