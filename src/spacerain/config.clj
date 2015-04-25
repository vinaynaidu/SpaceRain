(ns spacerain.config
  (:require [clojure.java.io]
            [environ.core :refer [env]]))

(def props-path "/etc/spacerain.properties")

(defn prop-file-exists?
  []
  (.exists (clojure.java.io/as-file props-path)))

(defn load-props
  "Loads props from file if file exists; returns empty map otherwise"
  []
  (if (prop-file-exists?)
    (with-open [^java.io.Reader reader (clojure.java.io/reader props-path)]
      (let [props (java.util.Properties.)]
        (.load props reader)
        (into {} (for [[k v] props] [(keyword k) (read-string v)]))))
    {}))

(def props-map (load-props))

;; Environ automatically lowercases variables and replaces
;; underscores and periods with hyphens.
;; SLACK_WEBHOOK_URL becomes :slack-webhook-url
;; -------------------------------------------------------

(def SLACK_WEBHOOK_URL (or (env :slack-webhook-url)
                           (props-map :SLACK_WEBHOOK_URL)
                           "still NA"))
