(ns farbton.scenes
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the scenes API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/scenes/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/scenes")))

(defn create-scene
  "Creates a new scene"
  [username {ip-address :internalipaddress} attributes]
  (:body (client/post (build-uri username
                                 ip-address)
                      {:form-params attributes
                       :content-type :json
                       :as :json})))

(defn request-scenes
  "Gets information about all available scenes"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username
                                ip-address)
                     {:as :json})))

(defn request-scene
  "Gets detailed information about a given scene"
  [username {ip-address :internalipaddress} scene-id]
  (:body (client/get (build-uri username
                                ip-address
                                scene-id)
                     {:as :json})))

(defn set-lightstates-attributes
  "Updates attributes of lightstates for a given scene"
  [username {ip-address :internalipaddress} scene-id light-id attributes]
  (:body (client/put (build-uri username
                                ip-address
                                (str scene-id "/lightstates/" light-id))
                     {:form-params attributes
                      :content-type :json
                      :as :json})))

(defn set-scene-attributes
  "Updates attribites for a given scene"
  [username {ip-address :internalipaddress} scene-id attributes]
  (:body (client/put (build-uri username
                                ip-address
                                scene-id)
                     {:form-params attributes
                      :content-type :json
                      :as :json})))

(defn delete-scene
  "Deletes a scene. Use carefully."
  [username {ip-address :internalipaddress} scene-id]
  (:body (client/delete (build-uri username
                                   ip-address
                                   scene-id)
                        {:as :json})))

