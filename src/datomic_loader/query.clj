(ns datomic-loader.query
  (:require [datomic-loader.client :refer [conn->]]
            [datomic.client.api :as d]
            [clojure.java.io :as io]))


(def conn (conn->))
(def db (d/db conn))

;; GET all people
(d/q '[:find ?entity ?label
       :where
       [?entity :rdfs/:label ?label]
       [?entity :rdf/type ?type]
       [?type :rdfs/:label "Person"]]
     db)

;; GET all people related to DT by id
(d/q '[:find ?label ?relation-label
       :in $ ?entity-id
       :where
       [?entity-id :rdfs/:label ?label]
       [?entity-id :tw/:relationship ?relation]
       [?relation :rdfs/:label ?relation-label]]
     db 17592186045741)

;; GET all people related to DT by label
(d/q '[:find ?label ?relation-label
       :in $ ?label
       :where
       [?entity :rdfs/:label ?label]
       [?entity :tw/:relationship ?relation]
       [?relation :rdfs/:label ?relation-label]]
     db "DONALD J. TRUMP")
