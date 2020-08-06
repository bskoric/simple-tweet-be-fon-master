(defproject simple-tweet "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [mysql/mysql-connector-java "8.0.21"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.2"]
                 [ring "1.8.1"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [org.clojure/data.json "0.2.7"]]
  :repl-options {:init-ns simple-tweet.core})
