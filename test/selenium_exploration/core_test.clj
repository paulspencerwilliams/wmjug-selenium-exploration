(ns selenium-exploration.core-test
  (:require [clojure.test :refer :all]
            [selenium-exploration.core :refer :all])
  (:import (org.openqa.selenium WebDriver By)
           (org.openqa.selenium.firefox FirefoxDriver)))

(def expected #{{:text "Clojure - Rationale"}
                {:text "Clojure is boring | 8th Light"}
                {:text "Clojure is still not for geniuses - Adam Bard and his magical blog"}
                {:text "Clojure: the good, the bad and the ugly | No silver bullet"}
                {:text "The Beauty of Clojure - Owen Rees-Hayward"}
                {:text "What is Clojure NOT good for? - Google Groups"}
                {:text "What is Clojure good at? | Hacker News"}
                {:text "What's so great about Clojure? - Software Engineering Stack Exchange"}
                {:text "Why Clojure's a Great Next Language for Rubyists"}
                {:text "Why would someone learn Clojure? - Quora"}})

(defn search []
  (with-open [driver (new FirefoxDriver)]
    (.get driver "https://google.co.uk")
    (.sendKeys (.findElement driver (By/name "q")) (into-array ["clojure is great"]))
    (.submit (.findElement driver (By/name "q")))
    (Thread/sleep 3000)
    (.click (.findElement driver (By/name "btnG")))
    (Thread/sleep 3000)
    (set
      (map
        (fn [a] {:text (.getText a)})
        (.findElements driver (By/cssSelector "div.srg h3 a"))))))


(deftest searching
  (testing "I can query results"
    (is
      (= expected
         (search)))))
