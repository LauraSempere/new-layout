#!/usr/bin/env boot

#tailrecursion.boot.core/version "2.5.1"

(set-env!
  :project      'new-layout
  :version      "0.1.0-SNAPSHOT"
  :dependencies '[[tailrecursion/boot.task   "2.2.4"]
                  [tailrecursion/hoplon      "5.10.24"]
                  [exicon/hoplon5-semantic-ui "1.10.2-SNAPSHOT"]]
  :out-path     "resources/public"
  :src-paths    #{"src/hl" "src/cljs" "src/clj"})

;; Static resources (css, images, etc.):
(add-sync! (get-env :out-path) #{"assets"})

(require '[tailrecursion.hoplon.boot :refer :all]
         '[tailrecursion.castra.task :as c])

(deftask development
  "Build new-layout for development."
  []
  (comp (watch) (hoplon {:prerender false}) (c/castra-dev-server 'new-layout.api)))

(deftask dev-debug
  "Build new-layout for development with source maps."
  []
  (comp (watch) (hoplon {:pretty-print true
                         :prerender false
                         :source-map true}) (c/castra-dev-server 'new-layout.api)))

(deftask production
  "Build new-layout for production."
  []
  (hoplon {:optimizations :advanced}))
