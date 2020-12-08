(ns demo.core-test
  (:require [clojure.test :refer :all]
            [demo.core :refer :all]))

(deftest test-overlaps-are-correctly-detected
  (testing "that when date-ranges overlap it is detected"
    (let [start-first #inst "2019-01-02"
          end-first #inst "2019-01-03T17:00"
          start-second #inst "2018-12-31"
          end-second #inst "2019-01-02T17:30"
          start-third #inst "2019-01-03T17:00"
          end-third #inst "2019-01-03T18:00"]
      (is (= true (overlap? start-first end-first start-second end-second)))
      (is (= false (overlap? start-first end-first start-third end-third))))))

