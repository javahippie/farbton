(ns farbton.sensors
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the sensors API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/sensors/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/sensors")))

(defn create-sensor
  "Creates a new sensor"
  [username {ip-address :internalipaddress} attributes]
  (:body (client/post (build-uri username
                                 ip-address)
                      {:form-params attributes
                       :content-type :json
                       :as :json})))

(defn search-for-new-sensors
  "Scan the networks for new sensors"
  [username {ip-address :internalipaddress}]
  (:body (client/post (build-uri username
                                ip-address)
                     {:as :json})))

(defn request-new-sensors
  "Gets the sensors that were discovered during the last scan"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username
                                ip-address
                                "new"))))


(defn request-sensors
  "Gets a list of all existing sensors"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username
                                ip-address)
                     {:as :json})))

(defn request-sensor
  "Gets detailed information of a given sensor"
  [username {ip-address :internalipaddress} sensor-id]
  (:body (client/get (build-uri username
                                ip-address
                                sensor-id)
                     {:as :json})))

(defn set-sensor-name
  "Updates the name of the given sensor"
  [username {ip-address :internalipaddress} sensor-id name]
  (:body (client/put (build-uri username
                                ip-address
                                sensor-id)
                     {:form-params {:name name}
                      :content-type :json
                      :as :json})))

(defn change-sensor-config
  "Updates the configuration of the given sensor"
  [username {ip-address :internalipaddress} sensor-id properties]
  (:body (client/put (build-uri username
                                ip-address
                                (str sensor-id "/config"))
                     {:form-params properties
                      :content-type :json
                      :as :json})))

(defn change-sensor-state
  "Updates the state of a CLIP sensor"
  [username {ip-address :internalipaddress} sensor-id properties]
  (:body (client/put (build-uri username
                                ip-address
                                (str sensor-id "/state"))
                     {:form-params properties
                      :content-type :json
                      :as :json})))

(defn delete-sensor
  "Deletes a given sensor. Use carefully."
  (:body (client/delete (build-uri username
                                   ip-address
                                   sensor-id))))
