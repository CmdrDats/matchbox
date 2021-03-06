(defproject matchbox "0.0.7-SNAPSHOT"
  :description "Firebase bindings for Clojure(Script)"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :url "http://github.com/crisptrutski/matchbox"
  :authors ["verma", "crisptrutski"]
  :min-lein-version "2.5.0"
  :dependencies [[org.clojure/clojure "1.6.0" :scope "provided"]
                 [org.clojure/clojurescript "0.0-3211" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha" :scope "provided"]
                 [com.firebase/firebase-client-jvm "2.2.4" :exclusions [org.apache.httpcomponents/httpclient]]
                 [org.apache.httpcomponents/httpclient "4.4.1"]

                 [cljsjs/firebase "2.2.7-0"]]
  :deploy-repositories [["releases" :clojars]]

  :test-paths ["test", "target/test-classes"]

  :jar-exclusions [#"\.cljx|\.swp|\.swo|\.DS_Store"]

  :auto-clean false

  :profiles {:dev {:dependencies [[com.keminglabs/cljx "0.6.0" :exclusions [com.cemerick/piggieback]]
                                  [com.cemerick/piggieback "0.2.1"]]
                   :repl-options {:nrepl-middleware
                                  [cljx.repl-middleware/wrap-cljx
                                   cemerick.piggieback/wrap-cljs-repl]}
                   :plugins [[com.keminglabs/cljx "0.6.0"]
                             [quickie "0.3.6"]
                             [lein-cljsbuild "1.0.3" :scope "test"]
                              [com.jakemccrary/lein-test-refresh "0.6.0"]
                             [com.cemerick/clojurescript.test "0.3.3" :scope "test"]
                             [com.keminglabs/cljx "0.6.0" :exclusions [com.cemerick/piggieback]]
                             [com.cemerick/piggieback "0.2.1"]]

                   :aliases {"test-all"  ["do" "cljx" "once,"
                                               "test,"
                                               "cljsbuild" "once" "test"]}}}

  :aot [matchbox.clojure.android-stub]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src", "test", "target/classes", "target/test-classes"]
                        :notify-command ["phantomjs" :cljs.test/runner "target/cljs/test.js"]
                        :compiler {:output-to "target/cljs/test.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]
              :test-commands {"unit-tests" ["phantomjs" :runner
                                            "this.literal_js_was_evaluated=true"
                                            "target/cljs/test.js"]}}

  :cljx {:builds [{:source-paths ["src"]
                   :output-path "target/classes"
                   :rules :clj}

                  {:source-paths ["src"]
                   :output-path "target/classes"
                   :rules :cljs}

                  {:source-paths ["test"]
                   :output-path "target/test-classes"
                   :rules :clj}

                  {:source-paths ["test"]
                   :output-path "target/test-classes"
                   :rules :cljs}]})
