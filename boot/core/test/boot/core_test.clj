(ns boot.core-test
  (:require [clojure.test :refer :all]
            [boot.core :refer :all]))

(def test-task)

(deftest task-options!-test
  (testing "set default task options"
    (with-redefs [test-task (fn [& xs] xs)]
      (is (= nil (test-task)))

      (task-options! test-task {:foo :bar})
      (is (= [:foo :bar] (test-task)))))

  (testing "override default task options"
    (with-redefs [test-task (fn [& xs] xs)]
      (is (= nil (test-task)))

      (task-options! test-task {:foo :bar})
      (is (= [:foo :bar] (test-task)))

      (task-options! test-task {:foo :frobozz})
      (is (= [:foo :bar :foo :frobozz] (test-task)))))

  (testing "update options with a function"
    (with-redefs [test-task (fn [& xs] xs)]
      (task-options! test-task {:foo :bar})
      (is (= [:foo :bar] (test-task)))

      (task-options! test-task #(assoc % :baz 1))
      (is (= [:foo :bar :foo :bar :baz 1] (test-task))))))
