(ns farbton.lights
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the lights API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/lights/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/lights")))

(defn search-for-new-lights
  "Scans the network for new devices"
  [username {ip-address :internalipaddress}]
  (:body (client/post (build-uri username 
                                 ip-address) 
                      {:as :json})))
 
(defn request-new-lights
  "Returns a list of lights which are found during the last scan"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username 
                                ip-address 
                                "new") 
                             {:as :json})))

(defn rename-light
  "Sets a new name for the given light"
  [username {ip-address :internalipaddress} light name]
  (:body (client/put (build-uri username 
                                ip-address 
                                light) 
                     {:form-params {:name name} 
                      :content-type :json
                      :as :json})))

(defn request-lights
  "Returns a list of available lights"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username 
                                ip-address) 
                     {:as :json})))

(defn request-light
  "Returns information about the queried light"
  [username {ip-address :internalipaddress} light]
  (:body (client/get (build-uri username 
                                ip-address 
                                light) 
                     {:as :json})))

(defn change-light-state
  "Changes the state of the light"
  [username {ip-address :internalipaddress} light params]
  (:body (client/put (build-uri username 
                                ip-address 
                                (str light "/state")) 
                     {:form-params params
                      :content-type :json
                      :as :json})))

(defn delete-light
  "Deletes a light. Use with caution!"
  [username {ip-address :internalipaddress} light]
  (:body (client/delete (build-uri username
                                   ip-address
                                   light)
                        {:as :json})))
