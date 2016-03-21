(ns test1.core
  (:gen-class))

(require '[clojure.java.jdbc :as j])
;(use [clojure.contrib.sql :as sql])
(use 'clojure.pprint 'clojure.reflect)



(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/restaurant"
               :user "root"
               :password "fpl350clark"})



(j/insert! mysql-db :restaurantattr
           {:restaurantId 1 :restaurantName "Mastros" :location "Lincoln Park" :price 15.0 :cuisine "Steakhouse" :allergyAlerts "peanuts" :atmosphere "fun" :rating 3}
           {:restaurantId 2 :restaurantName "Wild Ginger" :location "Downtown" :price 20.0 :cuisine "Asian" :allergyAlerts "peanuts, shellfish" :atmosphere "lively" :rating 3}
           {:restaurantId 3 :restaurantName "Bombay Cafe" :location "Downtown" :price 25.0 :cuisine "Asian" :allergyAlerts "none" :atmosphere "casual" :rating 4}
           {:restaurantId 4 :restaurantName "Sammy's" :location "Lincoln Park" :price 35.0 :cuisine "Steakhouse" :allergyAlerts "none" :atmosphere "fun" :rating 3}
           {:restaurantId 5 :restaurantName "Boneyard" :location "Downtown" :price 25.0 :cuisine "BBQ" :allergyAlerts "none" :atmosphere "lively" :rating 2}
           {:restaurantId 6 :restaurantName "Boss Hog" :location "Downtown" :price 10.0 :cuisine "BBQ" :allergyAlerts "none" :atmosphere "casual" :rating 3}
           {:restaurantId 7 :restaurantName "Appletons" :location "Lincoln Park" :price 15.0 :cuisine "Steakhouse" :allergyAlerts "none" :atmosphere "fun" :rating 4}
           {:restaurantId 8 :restaurantName "Mandarian" :location "Downtown" :price 25.0 :cuisine "Asian" :allergyAlerts "peanuts" :atmosphere "lively" :rating 4}
           {:restaurantId 9 :restaurantName "Tex Mex" :location "Lincoln Park" :price 23.0 :cuisine "BBQ" :allergyAlerts "none" :atmosphere "casual" :rating 3}
           {:restaurantId 10 :restaurantName "Cross Roads" :location "Downtown" :price 8.0 :cuisine "Steakhouse" :allergyAlerts "shellfish" :atmosphere "lively" :rating 3}
           )



(defn rule1[location price]
    "Location and price are restricted, so query
    database on given attributes."
  (println(str location))
  (println (str price))
  
  (j/query mysql-db
  ["select restaurantName, location, price from restaurantattr where location = ? AND price <= ?" location price]
  :row-fn println)
  (println)

)

(defn rule2[location]
    "Location is restricted, so query
    database on given attribute."
  (println(str location))
  
  (j/query mysql-db
  ["select restaurantName, location from restaurantattr where location = ? " location ]
  :row-fn println)
  (println)

)

(defn rule3[price]
  (println (str price))
    "Price is restricted, so query
    database on given attribute."
  (j/query mysql-db
  ["select restaurantName, price  from restaurantattr where price <= ?"  price]
  :row-fn println)
  (println)

)

(defn rule4[cuisine]
  (println(str cuisine))
   "Cuisine is restricted, so query
    database on given attribute."
  
  (j/query mysql-db
  ["select restaurantName, cuisine from restaurantattr where cuisine = ?" cuisine]
  :row-fn println)
  (println)

)

(defn rule5[cuisine rating price]
  (println(str cuisine))
  (println(str rating))
  (println (str price))
   "Cuisine, rating and price is restricted, so query
    database on given attributes."
  
  (j/query mysql-db
  ["select restaurantName, cuisine, rating, price from restaurantattr where cuisine = ? AND rating >= ? AND price <= ?" cuisine rating price]
  :row-fn println)
  (println)

)

(defn rule6[cuisine allergyAlerts]
  (println(str cuisine))
  (println (str allergyAlerts))
   "Cuisine and does not contain allergyAlerts is
    restricted, so query database on given attributes."
  (j/query mysql-db
  ["select restaurantName, cuisine, allergyAlerts from restaurantattr where cuisine = ? AND allergyAlerts != ?" cuisine allergyAlerts]
  :row-fn println)
  (println)

)

(defn rule7[startswith]
   "Client trying to figure out a name, so query
    database on given attribute."
  (println(str startswith))
  
  (j/query mysql-db
  ["select restaurantName from restaurantattr where restaurantName LIKE ? " startswith]
  :row-fn println)
  (println)

)



(defn -main []
  "I can say 'Hello World'."
  (println "Hello, World!")
  (println)
  (rule1 "Downtown" 15.0)
  (rule2 "Lincoln Park")
  (rule3 20.0)
  (rule4 "Steakhouse")
  (rule5 "Steakhouse" 3 45)
  (rule6 "Steakhouse" "shellfish")
  (rule7 "bo%")

  )