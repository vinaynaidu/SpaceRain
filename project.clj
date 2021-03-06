(defproject spacerain "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.3"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [cheshire "5.4.0"]
                 [clj-http "1.1.0"]
                 [com.taoensso/timbre "3.4.0"]
                 ;[http-kit "2.1.19"]
                 [ring-cors "0.1.7"]
                 [environ "1.0.0"]]
  :ring {:handler spacerain.core/app}
  :main ^:skip-aot spacerain.core
  :target-path "target/%s"
  :min-lein-version "2.0.0"
  :profiles {:uberjar {:aot :all}})
