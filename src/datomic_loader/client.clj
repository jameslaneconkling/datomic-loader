(ns datomic-loader.client
  (:require [datomic.client.api :as d]
            [environ.core :refer [env]]))


(def config {:server-type :peer-server
             :access-key (env :access-key)
             :secret (env :secret)
             :endpoint (env :server-url)})

(def client (d/client config))

(defn conn->
  []
  (d/connect client {:db-name (env :db-name)}))
