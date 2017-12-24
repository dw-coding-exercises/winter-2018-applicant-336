(ns my-exercise.util.turbovote-test
  (:require [clojure.test :refer :all]
            [my-exercise.util.turbovote :refer :all]))

(deftest elections-url-test
  (testing "correct url is returned for a single ocd-id"
    (is (= "https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:al"
           (elections-url ["ocd-division/country:us/state:al"]))))
  (testing "correct url is returned for multiple ocd-ids"
    (is (= "https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:al,ocd-division/country:us/state:al/place:birmingham"
           (elections-url
             ["ocd-division/country:us/state:al",
              "ocd-division/country:us/state:al/place:birmingham"])))))
