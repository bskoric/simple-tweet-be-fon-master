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
           (GET "/" [] tweets-api/simple-body-page)

           (GET "/tweets" [] tweets-api/get-all-tweets)
           (wrap-params (GET "/tweets/user" params (tweets-api/get-all-tweets-by-user (:query-params params))))
           (POST "/tweets/friend" req (tweets-api/get-all-friends-tweets req))
           (POST "/tweets/insert" req (tweets-api/insert-tweet req))

           (wrap-params (GET "/user" params (user-api/get-user (:query-params params))))

           (route/not-found "Error, page not found!")
           )


(defn -main
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "9000"))]
    (server/run-server (->
                        (wrap-defaults #'app-routes api-defaults)
                        (wrap-cors :access-control-allow-origin [#".*"]
                                   :access-control-allow-headers ["Content-Type" "Authorization"]
                                  :available-media-types ["multipart/form-data" "application/json"])
                        )
                       {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
