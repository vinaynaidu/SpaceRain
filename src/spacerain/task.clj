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
  "refer: https://github.com/vinaynaidu/SpaceRain/blob/master/README.md#supported-commands")

(defn post-to-slack
  "posts given message to slack as a bot user"
  [message request]
  (let [params (get request :params)
        text (get params :text "")
        ;; Channel_id works across public/private groups and DM's as well
        channel (get params :channel_id)
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
        meanings (map :text (get body :definitions))]
    (if (not-empty meanings)
      (do
        (let [formatted-text (->> (interleave (rest (range)) (repeat ". ") meanings (repeat \newline))
                                  (apply str))]
          ;(println formatted-text)
          (post-to-slack {:text formatted-text} r)
          ;; return empty response for word that has meanings so slack command ends quietly
          ""
          ))
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

(defn urban-define
  "gets meanings from urban dictionary"
  [word r]
  (let [response (client/get (str "https://mashape-community-urban-dictionary.p.mashape.com/define?term=" word)
                             {:headers {"X-Mashape-Key" MASHAPE_API_KEY}})
        body (ch/parse-string (get-in response [:body]) true)
        random-def (->> body :list (rand-nth))
        formatted-text (str "definition: "
                            (:definition random-def)
                            "\nexample: "
                            (:example random-def))]
    ;(println formatted-text)
    (post-to-slack {:text formatted-text} r))
  "")
