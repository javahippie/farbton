(ns farbton.core
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

(defn request-lights
  "Returns a list of available lights"
  [username {ip-address :internalipaddress}]
  (let [response (client/get (str "http://" ip-address "/api/" username "/lights") 
                             {:as :json})]
    (:body response)))

(defn request-light
  "Returns information about the queried light"
  [username {ip-address :internalipaddress} light]
  (let [response (client/get (str "http://" ip-address "/api/" username "/lights/" light) 
                             {:as :json})]
    (:body response)))

(defn change-light-state
  "Changes the state of the light"
  [username {ip-address :internalipaddress} light params]
  (let [response (client/put (str "http://" ip-address "/api/" username "/lights/" light "/state") 
                             {:form-params params
                              :content-type :json
                              :as :json})]
    (:body response)))
