(ns datomic-loader.core
  (:require [datomic-loader.client :refer [conn->]]
            [datomic-loader.schema :refer [schema]]
            [datomic-loader.load :refer [facts->types
                                         facts->entities
                                         facts->datums]]
            [datomic-loader.parse :as parse]
            [datomic.client.api :as d]
            [clojure.java.io :as io])
  (:gen-class))


(def conn (conn->))


(defn file-data
  [filePath]
  (with-open
    [reader (io/reader filePath)]
    (->> reader
         parse/parse-csv
         ((juxt facts->types facts->entities facts->datums))
         (apply concat)
         doall)))


(d/transact conn {:tx-data (file-data "./file.csv")})


(defn -main
  [& args]
  (file->data "./file.csv"))
