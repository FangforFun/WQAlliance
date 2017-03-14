package common;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.Utils;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.module.GlobeConfigModule;
import com.jess.arms.http.GlobeHttpHandler;
import com.jess.arms.utils.UiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import common.im.NimController;
import gkzxhn.wqalliance.di.module.CacheModule;
import gkzxhn.wqalliance.di.module.ServiceModule;
import gkzxhn.wqalliance.mvp.model.api.Api;
import gkzxhn.wqalliance.mvp.model.entities.OrderEvidence;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by 方 on 2017/3/2.
 */

public class SuperApplication extends BaseApplication {
    private AppComponent mAppComponent;
//    private RefWatcher mRefWatcher;//leakCanary观察器

    private static List<OrderEvidence> mOrderEvidences = new ArrayList<>(); //证据集合
    private static int evidencesLength;

    public static List<OrderEvidence> getOrderEvidences() {
        return mOrderEvidences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(getAppModule())//baseApplication提供
                .clientModule(getClientModule())//baseApplication提供
                .globeConfigModule(getGlobeConfigModule())//全局配置
                .imageModule(getImageModule())//baseApplication提供
                .serviceModule(new ServiceModule())//需自行创建
                .cacheModule(new CacheModule())//需自行创建
                .build();
        Utils.init(this);
        // 初始化log  第二个参数为true会将log写入文件  context.getCacheDir()目录下
        LogUtils.init(true, true, 'v', getClass().getName());
        NimController.init(this);

        //Android7.0权限适配  android.os.FileUriExposedException 解决方法
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= 18) {
            builder.detectFileUriExposure();
        }
    }

    //将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例, 在getAppComponent()拿到对象后都可以直接使用
    public AppComponent getAppComponent () {
        return mAppComponent;
    }


    //BaseApplication为抽象类,必须实现getGlobeConfigModule,这里返回整个应用需要的配置信息(将app的全局配置信息封装进module,使用Dagger注入到需要配置信息的地方)
    @Override
    protected GlobeConfigModule getGlobeConfigModule() {
        return GlobeConfigModule
                .buidler()
                .baseurl(Api.BASE_URL)
                .globeHttpHandler(new GlobeHttpHandler() {// 这里可以提供一个全局处理http响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        //这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                        //重新请求token,并重新执行请求
                        try {
                            if (!TextUtils.isEmpty(httpResult)) {
                                JSONArray array = new JSONArray(httpResult);
                                JSONObject object = (JSONObject) array.get(0);
                                String login = object.getString("btn_login_shape");
                                String avatar_url = object.getString("avatar_url");
                                Timber.tag(TAG).w("result ------>" + login + "    ||   avatar_url------>" + avatar_url);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            return response;
                        }


                        //这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                        //注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                        // create a new request and modify it accordingly using the new token
//                    Request newRequest = chain.request().newBuilder().header("token", newToken)
//                            .build();

//                    // retry the request
//
//                    response.body().close();
                        //如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可

                        //如果不需要返回新的结果,则直接把response参数返回出去
                        return response;
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        //如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则返回request

                        //return chain.request().newBuilder().header("token", tokenId)
//                .build();
                        return request;
                    }
                })
                .responseErroListener(new ResponseErroListener() {
                    //     用来提供处理所有错误的监听
                    //     rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效
                    @Override
                    public void handleResponseError(Context context, Exception e) {
                        Timber.tag(TAG).w("------------>" + e.getMessage());
                        UiUtils.SnackbarText("net error");
                    }
                }).build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppComponent != null)
            this.mAppComponent = null;
//        if (mRefWatcher != null)
//            this.mRefWatcher = null;
    }
}
