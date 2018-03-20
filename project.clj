(defproject datomic-loader "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/data.csv "0.1.4"]
                 [com.datomic/client-pro "0.8.14"]
                 [environ "1.1.0"]]
  :main ^:skip-aot datomic-loader.core
  :target-path "target/%s"
  :plugins [[lein-environ "0.4.0"]]
  :profiles {:uberjar {:aot :all}})
