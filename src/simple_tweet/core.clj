(ns simple-tweet.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.params :refer :all]
            [ring.middleware.cors :refer :all]
            [simple-tweet.api.tweetAPI :as tweets-api]
            [simple-tweet.api.userAPI :as user-api])
  (:gen-class))


(defroutes app-routes

           (GET "/tweets" [] tweets-api/get-all-tweets)
           (wrap-params (GET "/tweets/user" params (tweets-api/get-all-tweets-by-user (:query-params params))))
           (POST "/tweet" req (tweets-api/get-tweet-by-id req))
           (wrap-params (GET "/tweet/likes" params (tweets-api/get-number-of-likes (:query-params params))))
           (POST "/tweets/friend" req (tweets-api/get-all-friends-tweets req))
           (POST "/tweets/insert" req (tweets-api/insert-tweet req))
           (PUT "/tweets/update" req (tweets-api/update-tweet req))
           (DELETE "/tweets/delete" req (tweets-api/delete-tweet req))

           (POST "/login" req (user-api/login req))
           (POST "/register" req (user-api/register req))

           (GET "/users" [] user-api/get-all-users)
           (wrap-params (GET "/user" params (user-api/get-user (:query-params params))))
           (POST "/users" req (user-api/get-users req))
           (POST "/users/friends" req (user-api/get-friends req))
           (POST "/users/followers" req (user-api/get-followers req))
           (POST "/users/non-friends" req (user-api/get-non-friends req))
           (POST "/users/add-friend" req (user-api/add-friend req))
           (DELETE "/users/remove-friend" req (user-api/remove-friend req))
           (PUT "/user/update" req (user-api/update-user req))
           (PUT "/user/password/update" req (user-api/change-password req))

           (POST "/like" req (tweets-api/add-like req))
           (POST "/like/check" req (tweets-api/check-like req))
           (DELETE "/like/remove" req (tweets-api/remove-like req))

           (route/not-found "Error, not found!")
           )


(defn -main
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "9000"))]
    (server/run-server (->
                        (wrap-defaults #'app-routes api-defaults)
                        (wrap-cors  :access-control-allow-origin [#".*"]
                                    :access-control-allow-headers ["Content-Type" "Authorization"]
                                    :access-control-allow-methods [:get :put :post :delete]
                                    :available-media-types ["multipart/form-data" "application/json"])
                        )
                       {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
