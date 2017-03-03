package common;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.Presenter;

public abstract class SuperActivity<p extends Presenter> extends BaseActivity<p> {
    protected SuperApplication mSuperApplication;

    @Override
    protected void ComponentInject()  {
        mSuperApplication = (SuperApplication) getApplication();
        setupActivityComponent(mSuperApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);
}
