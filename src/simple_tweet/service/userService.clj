(ns simple-tweet.service.userService
  (:require [simple-tweet.dao.userDao :as user-dao]
            [crypto.password.bcrypt :as bcrypt]))

(defn check-username-and-password "Chek user name and password for login" [username password]
  (try
     (let [encrypt_password (user-dao/find-password username)]
       (let [is-correct-pass (bcrypt/check password encrypt_password)]
         (if is-correct-pass
           (user-dao/find-user-by-username-password username encrypt_password)
           (throw (Exception. "Password or username is not correct"))
           )
         )
       )
     (catch Exception e
       (throw (Exception. "Password or username is not correct"))))
  )
