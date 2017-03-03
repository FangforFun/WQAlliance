package common;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobeConfigModule;
import com.jess.arms.di.module.ImageModule;
import com.jess.arms.widget.imageloader.ImageLoader;

import javax.inject.Singleton;

import dagger.Component;
import gkzxhn.wqalliance.di.module.CacheModule;
import gkzxhn.wqalliance.di.module.ServiceModule;
import gkzxhn.wqalliance.mvp.model.api.cache.CacheManager;
import gkzxhn.wqalliance.mvp.model.api.service.ServiceManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;

/**
 * Created by 方 on 2017/3/2.
 */

@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class, ImageModule.class, CacheModule.class, GlobeConfigModule.class})
public interface AppComponent {
    Application Application();

    //服务管理器,retrofitApi
    ServiceManager serviceManager();

    //缓存管理器
    CacheManager cacheManager();

    //Rxjava错误处理管理类
    RxErrorHandler rxErrorHandler();

    OkHttpClient okHttpClient();

    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    ImageLoader imageLoader();

    //gson
    Gson gson();

    //用于管理所有activity
    AppManager appManager();
}


