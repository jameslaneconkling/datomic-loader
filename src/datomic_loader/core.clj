(ns datomic-loader.core
  (:require [datomic-loader.client :refer [conn->]]
            [datomic-loader.schema :refer [schema]]
            [datomic-loader.load :refer [facts->types
                                         facts->entities
                                         facts->datums]]
            [datomic-loader.parse :as parse])
  (:gen-class))


(defn file-data
  [filePath]
  (with-open [reader (io/reader filePath)]
    (->> reader
         parse/parse-csv
         ((juxt facts->types facts->entities facts->datums))
         (apply concat)
         doall)))

(defn -main
  [& args]
  (let [conn (conn->)]
    (d/transact conn {:tx-data (schema)})
    (d/transact conn {:tx-data (file-data "./file.csv")})))
