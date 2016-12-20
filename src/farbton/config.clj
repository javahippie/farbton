(ns farbton.config
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the config API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/config/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/config")))

(defn discover-bridge
  "Tries to find a bridge in the local network with Philips UPnP tool. Return format is {:id xxxxx :internalipaddress xxxxx}"
  []
  (first (:body (client/get "https://www.meethue.com/api/nupnp" 
                            {:as :json}))))


(defn create-user
  "Queries the Hue bridge for a username and adds it to the whitelist"
  [{ip-address :internalipaddress} device]
  (first (:body (client/post (str "http://" ip-address "/api")
                             {:form-params {:devicetype (str device)}
                              :content-type :json
                              :as :json}))))

(defn delete-user-from-whitespace
  "Removes a User ID from the bridge"
  [username {ip-address :internalipaddress} user-id]
  (:body (client/delete (build-uri username
                                   ip-address
                                   user-id)
                        {:as :json})))

(defn request-config
  "Returns a list of configuration elements of the bridge"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username 
                                ip-address)
                     {:as :json})))

(defn set-config-attributes
  "Updates the attributes of the configurations"
  [username {ip-address :internalipaddress} attributes]
  (:body (client/put (build-uri username
                                ip-address)
                     {:form-params attributes
                      :content-type :json
                      :as :json})))

(defn request-stats
  "Returns general information about the system"
  [username {ip-address :internalipaddress}]
  (:body (client/get (str "http://" ip-address "/api/" username) 
                     {:as :json})))











