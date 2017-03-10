package gkzxhn.wqalliance.mvp.model.api.service;

import com.jess.arms.http.BaseServiceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by 方 on 2017/3/2.
 */
@Singleton
public class ServiceManager implements BaseServiceManager {
    private CommonService mCommonService;
    private ProtectionService mProtectionService;

    public ProtectionService getProtectionService() {
        return mProtectionService;
    }

    //如果需要添加service只需在构造方法中添加对应的service,
    //在提供get方法返回出去,只要在ServiceModule提供了该service Dagger2会自行注入
    @Inject
    public ServiceManager(CommonService commonService, ProtectionService protectionService){
        this.mCommonService = commonService;
        this.mProtectionService = protectionService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override
    public void onDestory() {

    }
}
