(ns status-im.ui.screens.accounts.views
  (:require-macros [status-im.utils.views :refer [defview letsubs]])
  (:require [clojure.string :as string]
            [re-frame.core :as re-frame]
            [status-im.ui.screens.chat.photos :as photos]
            [status-im.ui.screens.accounts.styles :as styles]
            [status-im.utils.utils :as utils]
            [status-im.ui.components.list.views :as list]
            [status-im.ui.components.status-bar.view :as status-bar]
            [status-im.ui.components.react :as react]
            [status-im.i18n :as i18n]
            [status-im.ui.components.icons.vector-icons :as icons]
            [status-im.ui.components.colors :as colors]
            [status-im.ui.components.common.common :as components.common]
            [status-im.ui.components.toolbar.view :as toolbar]
            [status-im.ui.screens.privacy-policy.views :as privacy-policy]))

(defn account-view [{:keys [address photo-path name public-key keycard-instance-uid]}]
  [react/touchable-highlight {:on-press #(re-frame/dispatch [:accounts.login.ui/account-selected address photo-path name public-key])}
   [react/view styles/account-view
    [photos/photo photo-path {:size styles/account-image-size}]
    [react/view styles/account-badge-text-view
     [react/view {:flex-direction :row}
      [react/text {:style          styles/account-badge-text
                   :ellipsize-mode :middle
                   :numberOfLines  1}
       name]
      (when keycard-instance-uid
        [icons/icon :main-icons/keycard {:color           colors/blue
                                         :container-style {:margin-left 7}}])]
     [react/text {:style styles/account-badge-pub-key-text}
      (utils/get-shortened-address public-key)]]
    [react/view {:flex 1}]
    [icons/icon :main-icons/next {:color (colors/alpha colors/gray 0.4)}]]])

(defview accounts []
  (letsubs [accounts [:accounts/accounts]]
    [react/view styles/accounts-view
     [status-bar/status-bar]
     [react/text {:style {:typography :header :margin-top 24 :text-align :center}}
      (i18n/label :t/unlock)]
     [react/view styles/accounts-container
      [react/view styles/accounts-list-container
       [list/flat-list {:data      (vals accounts)
                        :key-fn    :address
                        :render-fn (fn [account] [account-view account])}]]
      [react/view
       [components.common/button {:on-press #(re-frame/dispatch [:accounts.create.ui/create-new-account-button-pressed])
                                  :button-style styles/bottom-button
                                  :label    (i18n/label :t/generate-a-new-key)}]
       [react/view styles/bottom-button-container
        [components.common/button {:on-press    #(re-frame/dispatch [:accounts.recover.ui/recover-account-button-pressed])
                                   :label       (i18n/label :t/access-key)
                                   :background? false}]]]]]))
