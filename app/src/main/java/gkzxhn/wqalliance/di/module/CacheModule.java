package gkzxhn.wqalliance.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gkzxhn.wqalliance.mvp.model.api.cache.CommonCache;
import io.rx_cache.internal.RxCache;

/**
 * Created by 方 on 2017/3/2.
 */
@Module
public class CacheModule {

    @Singleton
    @Provides
    CommonCache provideCommonService(RxCache rxCache) {
        return rxCache.using(CommonCache.class);
    }
}
