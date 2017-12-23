(ns my-exercise.search
  (:require
    [clojure.string :as str]
    [hiccup.page :refer [html5]]
    [my-exercise.util.turbovote :as turbovote]))

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

(defn elections-header []
  [:div.elections-header "Upcoming Elections"])

(defn display-election
  "Displays election data from the turbovote api."
  [{:keys [description date website] :as data}]
  [:li.election
    ; TODO display voting-methods and voter-registration-methods
    [:span.election-description
      [:a {:href website} description]]
    [:span.election-date date]])

(defn elections-for-address
  "Displays upcoming elections for an address."
  [address-map]
  (let [elections-data (turbovote/fetch-upcoming-elections address-map)]
    (if (empty? elections-data)
      [:div.upcoming-elections.no-elections
        "No upcoming elections found for this address"]
      [:div.upcoming-elections
        (elections-header)
        [:ul
          (map display-election elections-data)]])))

(defn page [{params :params :as request}]
  (html5
   (header request)
   (display-address params)
   (elections-for-address params)))
