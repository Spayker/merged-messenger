package com.spandr.meme.core.activity.webview.logic.init.channel.vk;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.spandr.meme.core.activity.main.logic.notification.ViewChannelManager;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.EMPTY_STRING;

public class VkontakteWebViewChannel extends WebViewChannel {

    private String VKONTAKTE_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    @SuppressWarnings("unused")
    private VkontakteWebViewChannel(){}

    public VkontakteWebViewChannel(WebViewActivity activity,
                                  String url, String channelName) {
        if(url.isEmpty()){
            return;
        }
        this.activity = activity;
        this.mWebView = activity.getmWebView();
        this.url = url;
        this.channelName = channelName;
        init();
    }

    protected VkontakteWebViewChannel init() {
        //initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        mWebView.addJavascriptInterface(new VkJavaScriptInterface(), "HTMLOUT");
        return this;
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(VKONTAKTE_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    class VkJavaScriptInterface {

        private final String MESSAGE_NOTIFICATION_REGEX = "<em class=\"mm_counter\">([0-9]+)</em>";

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                Matcher m = Pattern.compile(MESSAGE_NOTIFICATION_REGEX).matcher(html);
                int notificationCounter = 0;
                while(m.find()) {
                    notificationCounter += Integer.valueOf(m.group(1));
                }

                ViewChannelManager viewChannelManager = ViewChannelManager.getInstance();
                Map<String, View> channelViews = viewChannelManager.getChannelViews();
                TextView channelTextView = (TextView) channelViews.get(channelName);
                if(channelTextView != null){
                    if(notificationCounter > 0) {
                        String formattedNotificationCounter = String.valueOf(notificationCounter);
                        if(notificationCounter < 9){
                            channelTextView.setText(String.format(" %s", formattedNotificationCounter));
                        } else {
                            channelTextView.setText(formattedNotificationCounter);
                        }
                        channelTextView.setVisibility(VISIBLE);
                    } else {
                        channelTextView.setText(EMPTY_STRING);
                        channelTextView.setVisibility(INVISIBLE);
                    }
                }
            });
        }
    }

}
