package gkzxhn.wqalliance.mvp.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.di.component.DaggerHomeComponent;
import gkzxhn.wqalliance.di.module.HomeModule;
import gkzxhn.wqalliance.mvp.contract.HomeContract;
import gkzxhn.wqalliance.mvp.presenter.HomePresenter;
import gkzxhn.wqalliance.mvp.ui.activity.MessageActivity;
import gkzxhn.wqalliance.mvp.ui.activity.MyOrderActivity;
import gkzxhn.wqalliance.mvp.ui.activity.ProtectionActivity;
import gkzxhn.wqalliance.mvp.widget.CircleIndicator;
import gkzxhn.wqalliance.mvp.widget.CustPagerTransformer;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by 方 on 2017/3/3.
 */

public class HomeFragment extends BaseContentFragment<HomePresenter> implements HomeContract.View {

    private ViewPager mViewpager;
    private CircleIndicator mCircleIndicator;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))//请将HomeModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null, false);
        mViewpager = (ViewPager) contentView.findViewById(R.id.viewpager);
        mCircleIndicator = (CircleIndicator) contentView.findViewById(R.id.indicator);

        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

//        添加parallax效果，使用PageTransformer就足够了
        mViewpager.setPageTransformer(false, new CustPagerTransformer(getActivity()));

        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View child = LayoutInflater.from(HomeFragment.this.getActivity()).inflate(R.layout.home_card, null, false);

                ImageView image = (ImageView) child.findViewById(R.id.image);
                TextView tv_center = (TextView) child.findViewById(R.id.tv_center);
                TextView tv_people_account = (TextView) child.findViewById(R.id.people_account);
                ImageView iv_home_image = (ImageView) child.findViewById(R.id.iv_home_image);

                switch (position) {
                    case 0:
                        image.setImageResource(R.drawable.message_big);
                        tv_center.setText("我的消息");
                        break;
                    case 1:
                        image.setImageResource(R.drawable.protection_big);
                        tv_center.setText("我要维权");
                        break;
                    case 2:
                        image.setImageResource(R.drawable.order_big);
                        tv_center.setText("我的订单");
                        break;
                }
                container.addView(child);
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                //跳转消息二级界面
                                HomeFragment.this.getActivity().startActivity(new Intent(HomeFragment.this.getActivity(), MessageActivity.class));
                                break;
                            case 1:
                                //跳转维权二级界面
                                HomeFragment.this.getActivity().startActivity(new Intent(HomeFragment.this.getActivity(), ProtectionActivity.class));
                                break;
                            case 2:
                                //跳转订单二级界面
                                //TODO ...订单
                                HomeFragment.this.getActivity().startActivity(new Intent(HomeFragment.this.getActivity(), MyOrderActivity.class));
                                break;
                        }
                    }
                });
                return child;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        mCircleIndicator.setViewPager(mViewpager);

        mViewpager.setOnPageChangeListener(mOnPageChangeListener);

        mViewpager.setCurrentItem(1);

        return contentView;
    }

    @Override
    protected void setTitleData() {
        mIvBack.setVisibility(View.GONE);
        mTvSubtitle.setVisibility(View.GONE);
        mTvTitle.setText("维权联盟");
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传bundle,里面存一个what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事,和message同理
     *
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

}