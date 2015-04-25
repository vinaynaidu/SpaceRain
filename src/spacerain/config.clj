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

(defn find-prop
  "shortcut method to find prop either in file on via env"
  [propkey]
  (or (env propkey)
      (props-map propkey)
      "NA"))

(def SLACK_WEBHOOK_URL (find-prop :SLACK_WEBHOOK_URL))
