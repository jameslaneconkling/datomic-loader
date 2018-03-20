(ns datomic-loader.schema)

(def schema
  [{:db/ident :rdfs/:label
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :rdf/:type
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}
   {:db/ident :tw/:relationship
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many}])
