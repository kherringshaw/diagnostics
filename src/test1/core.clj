(ns test1.core
  (:gen-class))

(require '[clojure.java.jdbc :as j])
;(use [clojure.contrib.sql :as sql])
(use 'clojure.pprint 'clojure.reflect)



(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/diagnostics"
               :user "root"
               :password "fpl350clark"})


;;most common programming errors defined and suggested fixes
(j/insert! mysql-db :issues
           {:issue "illegal start of expression" :def "This error occurs when an expression is expected but not found." :fix1 "An extra right parenthesis after the condition of an if-statement" :fix2 "Forgetting the right-hand side of an assignment statement." }
           {:issue  "not a statement" :def "This error occurs when a syntactically correct statement does not appear where it should." :fix1 "Writing an assignment statement without the assignment operator"  :fix2 "Misspelling else"}
           {:issue "cannot find symbol" :def "All identifiers in JAVA must be declared before being used" :fix1 "An identifier can only be declared once in the same scope" :fix2 "Misspelling else"}
           {:issue "variable . . . might not have been initialized" :def "An instance variable declared in a class has a default initial value. However, a local variable declared within a method does not." :fix1 "The variable must be initialized on every path from declaration" :fix2 ""}
           {:issue "invalid method declaration; return type required" :def "Every method except constructors must have a return type or void specified." :fix1 "The error frequently occurs because it is easy to misspell the name of a constructor" :fix2 " The error can occur if you write a statement after a return statement"}
           )





(defn rule1[issue]
  (println(str issue))
   "User received an error and they type in one word
    lookup, so query database on given word."
  (j/query mysql-db
  ["select def, fix1, fix2 from issues where issue LIKE ? "  issue]
  :row-fn println)
  (println)

)



(defn rule2[startswith]
   "Client trying to figure out a name, so query
    database on given attribute."
  (println(str startswith))
  
  (j/query mysql-db
  ["select def, fix1, fix2 from issues where issue LIKE ? " startswith]
  :row-fn println)
  (println)

)

(defn rule3[fix]
  (println(str fix))
   "User knows the fix and they type in one word
    lookup, so query database on given word."
  (j/query mysql-db
  ["select def, fix1, fix2 from issues where fix LIKE ? "  fix]
  :row-fn println)
  (println)

)




(defn -main []
  "I can say 'Hello World'."
  (println "Hello, World!")
  (println)

  (rule1 "illegal")
  (rule2 "can%")
  (rule3 "error")

  )