(ns spacerain.task
  (:require [spacerain
             [util :as u]
             [config :refer :all]]
            [clj-http.client :as client]
            [cheshire.core :as ch]
            [taoensso.timbre :as log]))

(defn help
  "sends list of commands to slack"
  []
  (let [tasks [{:method "define" :comment "Prints meaning of the word" :usage "define {word}"}
               {:method "ipinfo:" :comment "Prints IP geo location info" :usage "ipinfo {ip}"}
               {:method "pugme" :comment "The best feature ever" :usage "pugme"}]]
    ""
    ))

(defn post-to-slack
  "posts given message to slack as a bot user"
  [message]
  (let [payload (merge {:username "HU"
                        :icon_url "https://d37jpvxvnmgnc2.cloudfront.net/common/images/HU-sm.jpg"}
                       message)]
    (client/post SLACK_WEBHOOK_URL
                 {:body (ch/generate-string payload)})))

(defn define
  "defines the word"
  [word]
  (str "yo the word is" word))

(defn pugbomb
  "Posts N pug image urls from pugme.herokuapp.com to slack"
  [n]
  (let [response (client/get (str "http://pugme.herokuapp.com/bomb?count=" n))
        pugs (-> (ch/parse-string (get-in response [:body]) true) :pugs)]
    (first pugs)
    (doseq [p pugs]
      (post-to-slack {:text p})))
  "")
