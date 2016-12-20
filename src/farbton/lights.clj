(ns farbton.lights
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the lights API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/lights/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/lights")
    ))


(defn request-lights
  "Returns a list of available lights"
  [username {ip-address :internalipaddress}]
  (let [response (client/get (build-uri username ip-address) 
                             {:as :json})]
    (:body response)))

(defn request-light
  "Returns information about the queried light"
  [username {ip-address :internalipaddress} light]
  (let [response (client/get (build-uri username ip-address light) 
                             {:as :json})]
    (:body response)))

(defn change-light-state
  "Changes the state of the light"
  [username {ip-address :internalipaddress} light params]
  (let [response (client/put (build-uri username ip-address (str light "/state")) 
                             {:form-params params
                              :content-type :json
                              :as :json})]
    (:body response)))

(defn switch-all-on
  "Switches all available lamps on. Not sure, if useful"
  [username bridge]
  (let [lights (request-lights username bridge)
        all-light-ids (map #(name (first %)) lights)]
    (pmap #(change-light-state username 
                              bridge 
                              % 
                              {:on true}) 
         all-light-ids)))

(defn switch-all-off
  "Switches all available lamps off. Not sure, if useful"
  [username bridge]
  (let  [lights (request-lights username bridge)
        all-light-ids (map #(name (first %)) lights)]
    (pmap #(change-light-state username
                              bridge
                              %
                              {:on false})
         all-light-ids)))
