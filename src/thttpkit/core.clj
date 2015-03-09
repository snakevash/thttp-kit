(ns thttpkit.core
  (:gen-class)
  (:use org.httpkit.server
        [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]]))

;; (defn async-handler [ring-request]
;;   (with-channel ring-request channel
;;     (if (websocket? channel)
;;       (on-receive channel (fn [data]
;;                             (send! channel data)))
;;       (send! channel {:status 200
;;                       :headers {"Content-Type" "text/plain"}
;;                       :body "Long polling?"}))))

;; (run-server async-handler {:port 8080})

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "hello HTTP!"})

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defroutes all-routes
  (files "/"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (reset! server (run-server #'all-routes {:port 8080})))
;; (-main)
;; (stop-server)
