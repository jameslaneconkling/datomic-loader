(ns datomic-loader.core
  (:require [datomic.client.api :as d]
            [environ.core :refer [env]])
  (:gen-class))

;; (def cfg {:server-type :peer-server
;;           :access-key (env :access-key)
;;           :secret (env :secret)
;;           :endpoint (env :server-url)})

;; (def client (d/client cfg))

;; (def conn (d/connect client {:db-name (env :db-name)}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello World"))
