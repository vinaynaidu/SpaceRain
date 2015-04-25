(ns spacerain.core
  (:require [spacerain
             [util :as u]
             [task :as t]
             [config :refer :all]]
            [compojure
             [core :refer :all]
             [handler :as handler]
             [route :as route]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]])
  (:gen-class))

(defn hu-hook-handler
  [request]
  (let [params (get request :params)
        text (get params :text "")
        channel (get params :channel_name "_")
        user (get params :user_name)
        args (clojure.string/split text #" ")]

    ;; process text (commands)
    (case (first args)
      "help" (t/help)
      "pugme" (t/pugbomb 1)
      "define" (t/define (clojure.string/join " " (rest args)))
      nil)))

(defroutes app-routes
  (GET "/" [] "--==SPACERAIN==--")
  (POST "/hu-hook" [* :as r] (hu-hook-handler r)))

(def app
  (handler/site app-routes))

(defn -main
  [& args]

  ;(u/config-logger! "info")

  (let [port (Integer. (or (env :port) 5050))]
    (jetty/run-jetty app {:port port :join? false}))

  #_(if (> (count args) 0)
      (task-handler args)
      (t/pugbomb args)
      )

  )
