(ns spacerain.task
  (:require [spacerain
             [util :as u]
             [slack :as s]]
            [clj-http.client :as client]
            [cheshire.core :as ch]
            [taoensso.timbre :as log]))

(defn help
  "sends list of commands to slack"
  []
  (let [tasks [{:method "define" :comment "Prints meaning of the word" :usage "define {word}"}
               {:method "ipinfo:" :comment "Prints IP geo location info" :usage "ipinfo {ip}"}
               {:method "pugme" :comment "The best feature ever" :usage "pugme"}]]
    ))

(defn define
  "defines the word"
  [word]
  (println "yo the word is" word))

(defn pugbomb
  "Posts N pug image urls from pugme.herokuapp.com to slack"
  [n]
  (let [response (client/get (str "http://pugme.herokuapp.com/bomb?count=" n))
        pugs (-> (ch/parse-string (get-in response [:body]) true) :pugs)]
    (first pugs)
    ))
