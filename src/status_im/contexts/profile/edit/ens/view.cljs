(ns status-im.contexts.profile.edit.ens.view
  (:require
   [quo.core :as quo]
   [react-native.core :as rn]
   [react-native.safe-area :as safe-area]
   [status-im.contexts.profile.edit.ens.style :as style]
   [utils.i18n :as i18n]
   [utils.re-frame :as rf]))

(defn view
  []
  (let [insets    (safe-area/get-insets)
        user-name (rf/sub [:get-screen-params :edit-ens])]
    (fn []
      [quo/overlay
       {:type            :shell
        :container-style (style/page-wrapper insets)}
       [quo/page-nav
        {:key        :header
         :background :blur
         :icon-name  :i/close
         :on-press   #(rf/dispatch [:navigate-back])}]
       [rn/view
        {:key   :content
         :style style/screen-container}
        [rn/view {:style {:gap 22}}
         [quo/text-combinations {:title user-name}]]
        [rn/view {:style style/button-wrapper}
         [quo/button
          {:type      :danger
           :blur?     true
           :icon-left :i/delete
           :on-press  (fn []
                        (rf/dispatch [:ens/remove-ens user-name])
                        (rf/dispatch [:navigate-back]))}
          (i18n/label :t/remove)]]]])))
