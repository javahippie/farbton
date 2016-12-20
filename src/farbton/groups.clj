(ns farbton.groups
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the groups API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/groups/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/groups")))

(defn create-group
  "Creates a new group"
  [username {ip-address :internalipaddress} params]
  (:body (client/post (build-uri username
                                 ip-address)
                      {:form-params params
                       :content-type :json
                       :as :json})))

(defn request-groups
  "Returns a list of all existing groups"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username
                                ip-address)
                     {:as :json})))

(defn request-group
  "Returns the details to a given group"
  [username {ip-address :internalipaddress} group-id]
  (:body (client/get (build-uri username
                                ip-address
                                group-id)
                     {:as :json})))

(defn set-group-attributes
  "Updates the attributes of a group (name, lights, class membership)"
  [username {ip-address :internalipaddress} group-id properties]
  (:body (client/put (build-uri username
                                ip-address
                                group-id)
                     {:form-params properties
                      :content-type :json
                      :as :json})))

(defn delete-group
  "Deletes a group. Use carefully"
  [username {ip-address :internalipaddress} group-id]
  (:body (client/delete (build-uri username
                                   ip-address
                                   group-id))))

(defn change-group-state
  "Modifies the state of lights in the whole group"
  [username {ip-address :internalipaddress} group-id properties]
  (:body (client/put (build-uri username
                                ip-address
                                (str group-id "/action"))
                     {:form-params properties
                      :content-type :json
                      :as :json})))
