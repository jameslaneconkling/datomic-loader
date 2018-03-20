(ns datomic-loader.load
  (:require [datomic-loader.parse :as parse]
            [clojure.java.io :as io]))


(defn facts->types
  [facts]
  (->> facts
       (mapcat (juxt :from-type :to-type))
       distinct
       (map #(hash-map :db/id %
                       :rdfs/:label %)))) ;; TODO - types should have a rdf/:type?

(defn fact->entities
  [{:keys [from from-type to to-type]}]
  (vector {:label from :type from-type}
          {:label to :type to-type}))

(defn facts->entities
  [facts]
  (->> facts
       (mapcat fact->entities)
       distinct
       (map #(hash-map :db/id (:label %)
                       :rdfs/:label (:label %)
                       :rdf/:type (:type %)))))

(defn facts->datums
  [facts]
  (map #(hash-map :db/id (:from %)
                  :tw/:relationship (:to %))
       facts))

;; (with-open
;;   [reader (io/reader "./file.csv")]
;;   (->> reader
;;        parse/parse-csv
;;        (take 30)
;;        ((juxt facts->types facts->entities facts->datums))
;;        (apply concat)
;;        doall))
