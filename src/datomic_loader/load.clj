(ns datomic-loader.load
  (:require [datomic-loader.parse :as parse]
            [clojure.java.io :as io]))


(def schema
  [{:db/ident :rdfs/:label
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :rdf/type
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}
   {:db/ident :tw/:relationship
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many}])

(defn facts->types
  [facts]
  (->> facts
       (mapcat (juxt :from-type :to-type))
       distinct
       (map #(hash-map :db/id %
                       :rdfs/:label %)))) ;; TODO - types should have a rdfs/:type?

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
                       :rdfs/:type (:type %)))))

(defn facts->datums
  [facts]
  (->> facts))

(with-open
  [reader (io/reader "./file.csv")]
  (->> reader
       parse/parse-csv
       (take 30)
       ((juxt facts->types facts->entities))
       second
       doall))
