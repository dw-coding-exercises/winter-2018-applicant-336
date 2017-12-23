(ns my-exercise.util.ocd-id
  (:require [clojure.string :as str]))

(defn sanitize
  "Returns a sanitized a string to use in an ocd-id"
  [s]
  (-> s
    (str/lower-case)
    (str/replace #"\s+" "_")))

(def key-order '(:country :state :place))
(def default-map {:country "us"})

(defn- build-part
  [map key]
  (when-let [value (or (key map) (key default-map))]
    (str "/" (name key) ":" (sanitize value))))

(defn build-id
  "Returns an ocd-id given a map of parts.
  Valid keys:
    - country (default: us)
    - state
    - place"
  [parts-map]
  (apply str "ocd-division" (mapcat (partial build-part parts-map) key-order)))

(defn ids-from-address
  "Returns a set of possible ocd-ids given an address."
  [{:keys [city state]}]
  #{(build-id {}) ; country:us
    (build-id {:state state})
    (build-id {:state state :place city})})
