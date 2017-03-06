package gkzxhn.wqalliance.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import javax.inject.Inject;

import gkzxhn.wqalliance.mvp.contract.MyOrderContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:
 */
@ActivityScope
public class MyOrderPresenter extends BasePresenter<MyOrderContract.Model, MyOrderContract.View> {

    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public MyOrderPresenter(MyOrderContract.Model model, MyOrderContract.View view,
                            RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager){
        super(model, view);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
