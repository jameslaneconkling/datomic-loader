(ns datomic-loader.parse
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str]))

(defn csv-list->map
  [data]
  (map zipmap
       (repeat [:from-type :from :to-type :to :link :sources])
       (rest data)))

(defn split-sources
  [row]
  (update row :sources #(str/split % #" \| ")))

(defn parse-csv
  [reader]
  (->> reader
       csv/read-csv
       csv-list->map
       (map split-sources)))


;; Aggregation + Exploratory functions
(defn compare-desc
  [a b]
  (compare b a))

(defn row-count
  [rows]
  (list (count rows)))

(defn source-count
  [rows]
  (->> rows
       (map #(count (:sources %)))
       frequencies
       (sort-by second compare-desc)))

(defn type-count
  [rows]
  (->> rows
       (mapcat (comp vals #(select-keys % [:from-type :to-type])))
       frequencies
       (sort-by second compare-desc)))

(defn type-pair-count
  [rows]
  (->> rows
       (map (comp sort vals #(select-keys % [:from-type :to-type])))
       frequencies
       (sort-by second compare-desc)))

(defn link-count
  [rows]
  (->> rows
       (map :link)
       frequencies
       (filter #(> (second %) 4))
       (sort-by second compare-desc)))

(defn node-count
  [rows]
  (->> rows
       (mapcat (comp vals #(select-keys % [:from :to])))
       frequencies
       (filter (comp (partial < 4) second))
       (sort-by second compare-desc)))

;; usage
;;
;; (with-open
;;   [reader (io/reader filePath)]
;;   (->> reader
;;        parse-csv
;;        node-count
;;        (run! pprint)))
