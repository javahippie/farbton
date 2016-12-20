(ns farbton.schedules
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the groups API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/schedules/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/schedules")))

(defn create-schedule
  "Creates a new schedule"
  [username {ip-address :internalipaddress} params]
  (:body (client/post (build-uri username
                                 ip-address)
                      {:form-params params
                       :content-type :json
                       :as :json})))

(defn request-schedules
  "Gets a list of all existing schedules"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username
                                ip-address)
                     {:as :json})))

(defn request-schedule
  "Gets detailed information about a given schedule"
  [username {ip-address :internalipaddress} schedule-id]
  (:body (client/get (build-uri username
                                ip-address
                                schedule-id)
                     {:as :json})))

(defn set-schedule-attributes
  "Updates the attributes of a given group"
  [username {ip-address :internalipaddress} schedule-id attributes]
  (:body (client/put (build-uri username
                                ip-address
                                schedule-id)
                     {:form-params attributes
                      :content-type :json
                      :as :json})))

(defn delete-schedule
  "Deletes a schedule. Use carefully."
  [username {ip-address :internalipaddress} schedule-id]
  (:body (client/delete (build-uri username
                                   ip-address
                                   schedule-id)
                        {:as :json})))
