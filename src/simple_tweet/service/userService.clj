(ns simple-tweet.service.userService
  (:require [simple-tweet.dao.userDao :as user-dao]
            [crypto.password.bcrypt :as bcrypt]
            [clojure.string :as string])
  (:import java.util.Base64)
  )

(defn decodeBase64 [to-decode]
  (String. (.decode (Base64/getDecoder) to-decode))
  )

(defn check-username-and-password "Chek user name and password for login" [username password]
  (try
     (let [encrypt_password (user-dao/find-password username)]
       (let [is-correct-pass (bcrypt/check (decodeBase64 password) encrypt_password)]
         (if is-correct-pass
           (user-dao/find-user-by-username-password username encrypt_password)
           (throw (Exception. "Password or username is not correct"))
           )
         )
       )
     (catch Exception e
       (throw (Exception. "Password or username is not correct"))))
  )


(defn change-password "Check old pass and change" [username old_password new_password]
  (try
    (let [encrypt_password (user-dao/find-password username)]
      (let [is-correct-pass (bcrypt/check (decodeBase64 old_password) encrypt_password)]
        (if is-correct-pass
          (let [new_pass_enc (bcrypt/encrypt (decodeBase64 new_password))]
          (user-dao/update-password username new_pass_enc)
          (throw (Exception. "Old password is not correct"))
          )
        )
        )
      )
    (catch Exception e
      (throw (Exception. (.getMessage e)))
      )
    )
  )

(defn register-user [firstname lastname username password email]
  {:pre [(not (string/blank? firstname)) (not (string/blank? lastname)) (not (string/blank? username))
         (not (string/blank? password)) (not (string/blank? email))]}
  (let [encrypted_pass (bcrypt/encrypt (decodeBase64 password))]
    (user-dao/insert-user firstname lastname username encrypted_pass email)
    (format "Successfully register user %s" username)
    ))