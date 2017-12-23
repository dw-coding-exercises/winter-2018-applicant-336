(ns my-exercise.search
  (:require
    [clojure.string :as str]
    [hiccup.page :refer [html5]]))

(defn header [_]
  [:head
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
   [:title "Election Search"]
   [:link {:rel "stylesheet" :href "default.css"}]])

(defn display-address
  [{:keys [street street-2 city state zip]}]
  [:div.address
    [:div.address-street street]
    (when-not (str/blank? street-2) [:div.address-street street-2])
    [:div.address-city-state (str city ", " state " " zip)]])

(defn page [{params :params :as request}]
  (html5
   (header request)
   (display-address params)))
