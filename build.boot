(set-env!
 :dependencies '[[tailrecursion/boot.task   "2.2.4"]
                 [tailrecursion/boot-hoplon "0.1.0-SNAPSHOT"]
                 [tailrecursion/hoplon "6.0.0-alpha1"]
                 [adzerk/boot-cljs-repl     "0.1.8"]
                 [adzerk/boot-reload        "0.2.6"]
                 [adzerk/boot-cljs          "0.0-2814-0"]
                 [cljsjs/boot-cljsjs "0.4.6" :scope "test"]
                 [pandeiro/boot-http "0.6.3-SNAPSHOT"]
                 [exicon/semantic-ui "1.12.2-SNAPSHOT"]]
 :resource-paths #{"assets" "src"}
 :source-paths #{"src"})

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload :refer [reload]]
 '[pandeiro.boot-http :refer [serve]]
 '[tailrecursion.boot-hoplon :refer [hoplon prerender html2cljs]]
 '[cljsjs.boot-cljsjs :refer [from-cljsjs]])

(def +version+ "0.1.0-SNAPSHOT")

(task-options!
 pom {
      :project 'exicon/apperize
      :description "Mobile friendly frontend separated out"
      :version +version+
      :url "http://www.exiconglobal.com"
      :license {"MIT" "http://opensource.org/licenses/MIT"}
      :scm {:url "https://github.com/exicon/apperize"}}
 cljs {:source-map true}
 )

(deftask deploy []
  (comp (pom) (jar) (push)))

(deftask dev []
  (comp
   (serve :port 3004)
   (from-cljsjs :profile :development)
   (sift :to-resource #{#"themes"})
   (sift :to-resource #{#"semantice-ui.inc.css"})
   (watch)
   (hoplon
    :pretty-print true
    :prerender true
    :source-map false
    )
   (reload)
   (cljs :optimizations :none)))

(deftask dev-debug []
  (task-options! cljs {:source-map false})
  (dev))
