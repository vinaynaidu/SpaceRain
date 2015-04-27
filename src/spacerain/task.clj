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
    "available commands: define: defines a word; pugme: posts a picture of a pug"
    ))

(defn post-to-slack
  "posts given message to slack as a bot user"
  [message request]
  (let [params (get request :params)
        text (get params :text "")
        channel_name (get params :channel_name)
        channel (if (some #{channel_name} ["privategroup" "directmessage"])
                  "_v_"
                  (str "#" channel_name))
        user (get params :user_name)
        full-message {:text (str user ": " text
                                 "\n\n"
                                 (get message :text))}
        payload (merge {:username "HU"
                        :icon_url "https://d37jpvxvnmgnc2.cloudfront.net/common/images/super-hu.jpg"
                        :channel channel}
                       full-message)]
    (client/post SLACK_WEBHOOK_URL
                 {:body (ch/generate-string payload)})))

(defn define
  "defines the word"
  [word r]
  (let [response (client/get (str "https://montanaflynn-dictionary.p.mashape.com/define?word=" word)
                             {:headers {"X-Mashape-Key" MASHAPE_API_KEY}})
        body (ch/parse-string (get-in response [:body]) true)
        meaning (first (get body :definitions))]
    (if (not-empty meaning)
      (do
        (post-to-slack {:text (get meaning :text)} r)
        ;; return empty response for word that has meanings so slack command ends quietly
        "")
      "no definition found")))

(defn pugbomb
  "Posts N pug image urls from pugme.herokuapp.com to slack"
  [n r]
  (let [response (client/get (str "http://pugme.herokuapp.com/bomb?count=" n))
        pugs (-> (ch/parse-string (get-in response [:body]) true) :pugs)]
    ;(first pugs)
    (doseq [p pugs]
      (post-to-slack {:text p} r)))
  "")
