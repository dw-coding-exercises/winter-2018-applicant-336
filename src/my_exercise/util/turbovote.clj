(ns my-exercise.util.turbovote
  "Turbovote api calls"
  (:require
    [clojure.string :as str]
    [clojure.edn :as edn]
    [my-exercise.util.ocd-id :as ocd]))

(def base-url "https://api.turbovote.org/")

(defn elections-url
  "Returns the turbovote elections url for a set of ocd-ids."
  [ocd-ids]
  (str base-url
       "elections/upcoming?"
       "district-divisions=" (str/join "," ocd-ids)))

(defn fetch-upcoming-elections
  "Returns upcoming elections data from the turbovote api given an address."
  [address-map]
  (let [ids (ocd/ids-from-address address-map)
        data (slurp (elections-url ids))]
    (edn/read-string data)))
