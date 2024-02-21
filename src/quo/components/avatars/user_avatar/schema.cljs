(ns quo.components.avatars.user-avatar.schema)

(def ^:private ?profile-picture-fn-params
  [:map
   [:length :int]
   [:full-name :string]
   [:font-size :int]
   [:indicator-size {:optional true} [:maybe :int]]
   [:indicator-color {:optional true} [:maybe :string]]
   [:override-theme :schema.common/theme]
   [:background-color :string]
   [:color :string]
   [:size :int]
   [:ring? :boolean]
   [:ring-width :int]])

(def ?profile-picture
  [:or
   :schema.common/image-source
   [:map
    [:fn [:=> [:cat ?profile-picture-fn-params] :string]]]])
