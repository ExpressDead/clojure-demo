(ns demo.core
  (:gen-class)
  (:import [java.util Date])
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def events
  "Collection of events to work with"
  (-> "events.edn"
      io/resource
      slurp
      edn/read-string))

(defn overlap?
  "Find out if the range for 2 events overlaps"
  [start end test-start test-end]
  (or (and (.after test-start start)
           (.before test-start end))
      (and (.after test-end start)
           (.before test-end end))))

(defn filter-events-to-overlaps
  "Filter events coll to to overlapping pairs"
  [events]
  (loop [current-evt (first events)
         remaining-evts (rest events)
         overlapping []]
    (if (empty? remaining-evts)
      (remove nil? overlapping)
      (recur (first remaining-evts)
             (rest remaining-evts)
             (concat overlapping (map #(if (overlap? (:start current-evt)
                                                   (:end current-evt)
                                                   (:start %)
                                                   (:end %))
                                       (vector current-evt %))
                                    remaining-evts))))))

(defn find-overlapping-events
  "Given a collection of events return all event pairs that have overlapping schedules"
  [events]
  (let [sorted-events (sort-by second events)
        filtered-events (filter-events-to-overlaps sorted-events)]
    filtered-events))

(defn -main
  "The entry point for 'lein run'"
  [& args]
  (print (find-overlapping-events events)))

