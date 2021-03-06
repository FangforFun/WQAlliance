package common;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.Presenter;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class SuperFragment<P extends Presenter> extends BaseFragment<P> {
    protected SuperApplication mSuperApplication;
    @Override
    protected void ComponentInject() {
        mSuperApplication = (SuperApplication) mActivity.getApplication();
        setupFragmentComponent(mSuperApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher watcher = WEApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
//        if (watcher != null) {
//            watcher.watch(this);
//        }
        this.mSuperApplication =null;
    }
}
