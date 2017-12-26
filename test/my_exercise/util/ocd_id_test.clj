(ns my-exercise.util.ocd-id-test
  (:require [clojure.test :refer :all]
            [my-exercise.util.ocd-id :refer :all]))

(deftest sanitize-test
  (testing "inputs are lowercased"
    (is (= "portland" (sanitize "Portland")))
    (is (= "chilhowie" (sanitize "cHilhOwIe"))))
  (testing "spaces are replaced with \"_\""
    (is (= "st_paul" (sanitize "St Paul")))
    (is (= "kansas_city" (sanitize "kansas city")))))

(deftest build-id-test
  (testing "us is the default country"
    (is (= "ocd-division/country:us" (build-id {}))))
  (testing "can override the default country"
    (is (= "ocd-division/country:canada" (build-id {:country "canada"}))))
  (testing "can build an id with all parts"
    (is (= "ocd-division/country:us/state:or/place:portland"
           (build-id {:state "or" :place "portland"})))
    (is (= "ocd-division/country:us/state:va/place:richmond"
           (build-id {:state "va" :place "richmond"}))))
  (testing "can build an id without all parts"
    (is (= "ocd-division/country:us/state:or"
           (build-id {:state "or"})))
    (is (= "ocd-division/country:us/place:richmond"
           (build-id {:place "richmond"}))))
  (testing "all parts are sanitized"
    (is (= "ocd-division/country:us/state:va/place:richmond"
           (build-id {:country "uS" :state "Va" :place "rIcHmoNd"})))))

(deftest ids-from-address-test
  (testing "parts are converted into the correct ocd-id keys"
    (is (contains? (ids-from-address {:city "richmond" :state "va"})
                   "ocd-division/country:us/state:va/place:richmond")))
  (testing "as many ids as possible are generated"
    (is (= #{"ocd-division/country:us"
             "ocd-division/country:us/state:va"
             "ocd-division/country:us/state:va/place:richmond"}
            (ids-from-address {:city "richmond" :state "va"})))
    (is (= #{"ocd-division/country:us"
             "ocd-division/country:us/state:va"}
            (ids-from-address {:state "va"})))
    (is (= #{"ocd-division/country:us"}
            (ids-from-address {})))))
