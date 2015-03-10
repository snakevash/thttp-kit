(ns thttpkit.core
  (:gen-class)
  (:use org.httpkit.server
        [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]])
  (:require [clojure.data.json :as json]))

;; 异步处理
;; (defn async-handler [ring-request]
;;   (with-channel ring-request channel
;;     (if (websocket? channel)
;;       (on-receive channel (fn [data]
;;                             (send! channel data)))
;;       (send! channel {:status 200
;;                       :headers {"Content-Type" "text/plain"}
;;                       :body "Long polling?"}))))
;; (run-server async-handler {:port 8080})

;; 简单处理接口范例
;; (defn app [req]
;;   {:status 200
;;    :headers {"Content-Type" "text/html"}
;;    :body "hello HTTP!"})

;; 定义服务器容器
(defonce server (atom nil))

;; 停止服务器
(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

;; json结果测试
(defn json-one [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str (class req))})

;; 定义所有的路由
(defroutes all-routes
  (files "/")
  (GET "/json/one" [] json-one))

;; 主入口函数
(defn -main
  "简单的后台接口"
  [& args]
  (reset! server (run-server #'all-routes {:port 8080})))

;; 一些测试函数 用于手动
;; (-main)
;; (stop-server)
