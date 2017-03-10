package gkzxhn.wqalliance.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gkzxhn.wqalliance.mvp.model.api.service.CommonService;
import gkzxhn.wqalliance.mvp.model.api.service.ProtectionService;
import retrofit2.Retrofit;

/**
 * Created by 方 on 2017/3/2.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

    @Singleton
    @Provides
    ProtectionService provideProtectionService(Retrofit retrofit) {
        return retrofit.create(ProtectionService.class);
    }
}