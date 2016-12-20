(ns farbton.global
  (:require [clj-http.client :as client]))

(defn discover-bridge
  "Tries to find a bridge in the local network with Philips UPnP tool. Return format is {:id xxxxx :internalipaddress xxxxx}"
  []
  (let [response (client/get "https://www.meethue.com/api/nupnp" {:as :json})]
    (first (get-in response [:body]))))


(defn request-username
  "Queries the Hue bridge for a username"
  [{ip-address :internalipaddress} device]
  (let [response (client/post (str "http://" ip-address "/api")
                              {:form-params {:devicetype (str "farbton:" device)}
                               :content-type :json
                               :as :json})]
    (first (:body response))))

(defn request-stats
  "Returns general information about the system"
  [username {ip-address :internalipaddress}]
  (let [response (client/get (str "http://" ip-address "/api/" username) 
                             {:as :json})]
    (:body response)))











