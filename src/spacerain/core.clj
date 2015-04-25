(ns spacerain.core
  (:require [spacerain
             [util :as u]
             [task :as t]]
            [compojure
             [core :refer :all]
             [handler :as handler]
             [route :as route]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] "--==SPACERAIN==--"))

(def app
  (handler/site app-routes))

(defn task-handler
  "router for task handlers"
  [args]
  (case (first args)
    "help" (t/help)
    "pugme" (t/pugbomb (or (second args) 1))
    "define" (t/define (clojure.string/join " " (rest args)))
    nil))

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
