(ns farbton.rules
  (:require [clj-http.client :as client]))

(defn- build-uri
  "Builds the base URI for the rules API"
  ([username hostname appendix]
   (str "http://" hostname "/api/" username "/rules/" appendix))
  ([username hostname]
    (str "http://" hostname "/api/" username "/rules")))

(defn create-rule
  "Creates a new rule"
  [username {ip-address :internalipaddress} params]
  (:body (client/post (build-uri username
                                 ip-address)
                      {:form-params params
                       :content-type :json
                       :as :json})))

(defn request-rules
  "Gets a list of all existing rules"
  [username {ip-address :internalipaddress}]
  (:body (client/get (build-uri username
                                ip-address)
                     {:as :json})))

(defn request-rule
  "Gets detaild information about the specified rule"
  [username {ip-address :internalipaddress} rule-id]
  (:body (client/get (build-uri username
                                ip-address
                                rule-id)
                     {:as :json})))

(defn set-rule-attributes
  "Updates the attributes of the specified rule"
  [username {ip-address :internalipaddress} rule-id attributes]
  (:body (client/put (build-uri username
                                ip-address
                                rule-id)
                     {:form-params attributes
                      :content-type :json
                      :as :json})))

(defn delete-rule
  "Deletes the specified rule. Use carefully"
  [username {ip-address :internalipaddress} rule-id]
  (:body (client/delete (build-uri username
                                   ip-address
                                   rule-id)
                        {:as :json})))
