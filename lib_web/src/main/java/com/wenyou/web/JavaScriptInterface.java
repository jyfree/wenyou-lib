package com.wenyou.web;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.wenyou.web.apiModule.ApiModuleManager;
import com.wenyou.web.apiModule.IApiModule;
import com.wenyou.web.apiModule.ResultData;
import com.wenyou.web.utils.WebLogUtils;

import java.lang.ref.WeakReference;

/**
 * @description js接口
 * @date: 2021/12/16 10:44
 * @author: jy
 */
public class JavaScriptInterface {
    private static final String TAG = "JavaScriptInterface";
    private Gson mGson = new Gson();

    private WeakReference<WebView> webViewHolder = null;
    //传单个参数
    private static final String INVOKE_WEB_METHOD =
            "javascript: try {var method = %s;var str = %s;var value = '';" +
                    "try {value = JSON.parse(str);} catch (e) {value = str;}window" +
                    ".WYApiCore.invokeWebMethod(method, value)} " +
                    "catch (e) {if (console) console.log(e)}";

    //传多个参数
    private static final String INVOKE_WEB_MUTI_PARAM
            = "javascript: try {"
            + "var method = %s;"
            + "var strs = %s;"
            + "var value = strs.split(',');"
            + "var params = new Array();"
            + "for (var i = 0; i < value.length; i++) {"
            + "var str = value[i];"
            + "var param ='';"
            + "try {"
            + "param = JSON.parse(str);"
            + "} catch (e) {"
            + "param = str;"
            + "}"
            + "params[i] = param;"
            + "}"
            + "window.WYApiCore.invokeWebMethod(method, params);"
            + "} catch (e) {"
            + "if (console) console.log(e);"
            + "};";

    public JavaScriptInterface(WebView view) {
        if (view != null) {
            webViewHolder = new WeakReference<WebView>(view);
        }
    }

    private ApiModuleManager apiModuleManager = new ApiModuleManager();

    public void addApiModule(IApiModule module) {
        apiModuleManager.addModule(module);
    }

    public void removeApiModule(String moduleName) {
        apiModuleManager.removeModuleByName(moduleName);
    }

    @TargetApi(11)
    public void release() {
        if (webViewHolder != null) {
            WebView webView = webViewHolder.get();
            if (webView != null) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    webView.removeJavascriptInterface("AndroidJSInterface");
                }
                apiModuleManager.release();
            }
        }
    }

    /*
     * web端API将此方法包装为 invokeClientMethod
     * */

    @JavascriptInterface
    public String invoke(final String module, final String name, final String parameters,
                         final String callback) {
        try {
            IApiModule apiModule = apiModuleManager.getModule(module);
            if (apiModule != null) {
                return apiModule.invoke(name, parameters, genJSCallback(callback));
            }
        } catch (Throwable e) {
            WebLogUtils.INSTANCE.e(TAG, "invoke module = "
                    + module + ", name = " + name + ", parameters = "
                    + parameters + ", error happen e = " + e, e);
        }
        return mGson.toJson(new ResultData(-1));
    }

    private Handler mtHandler = new Handler(Looper.getMainLooper());

    private void invokeJSCallback(final String cbName, final String jsonParam) {
        if (webViewHolder != null) {
            final WebView webView = webViewHolder.get();
            if (webView != null) {
                mtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String invokeStr = String.format(INVOKE_WEB_METHOD, cbName, jsonParam);
                            WebLogUtils.INSTANCE.i(TAG, invokeStr);
                            webView.loadUrl(invokeStr);
                        } catch (Exception e) {
                            WebLogUtils.INSTANCE.e(TAG, e.getMessage());
                        }
                    }
                });
            }
        }
    }

    private void invokeJSCallback(final String cbName, final String... jsonParam) {

        StringBuilder params = new StringBuilder();

        for (int i = 0; i < jsonParam.length; i++) {
            params.append(jsonParam[i]);
            if (jsonParam.length > (i + 1)) {
                params.append(",");
            }
        }

        if (webViewHolder != null) {
            final WebView webView = webViewHolder.get();
            if (webView != null) {
                mtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String invokeStr =
                                    String.format(INVOKE_WEB_MUTI_PARAM, cbName, params.toString());
                            WebLogUtils.INSTANCE.i(TAG, invokeStr);
                            webView.loadUrl(invokeStr);
                        } catch (Exception e) {
                            WebLogUtils.INSTANCE.e(TAG, e.getMessage());
                        }
                    }
                });
            }
        }
    }


    private IApiModule.IJSCallback genJSCallback(final String callbackName) {
        if (callbackName != null && callbackName.length() > 0) {
            return new IApiModule.IJSCallback() {
                @Override
                public void invokeCallback(String param) {
                    invokeJSCallback(callbackName, param);
                }

                @Override
                public void invokeCallback(String... param) {
                    invokeJSCallback(callbackName, param);
                }
            };
        }
        return null;
    }


}
