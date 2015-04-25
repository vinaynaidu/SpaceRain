(ns spacerain.util
  (:require [taoensso.timbre :as timbre]))

(defn config-logger!
  [loglevel]
  ;(println "Configuring logger..." ERR_LOG ", level: " loglevel)
  (timbre/set-level! loglevel)
  (timbre/set-config! [:timestamp-pattern] "yyyy-MMM-dd HH:mm:ss")
  (timbre/set-config! [:timestamp-locale] (java.util.Locale/UK))
  (timbre/set-config! [:appenders :spit :enabled?] true)
  ;;(timbre/set-config! [:shared-appender-config :spit-filename] ERR_LOG))
  (timbre/set-config! [:shared-appender-config :spit-filename] "/home/fedora-blackfyre/Downloads/log/spacerain.log"))

