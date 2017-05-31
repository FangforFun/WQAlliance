package gkzxhn.wqalliance.mvp.model.entities;

import android.view.View;

/**
 * 商品品牌提示条目点击事件
 * Created by 方 on 2017/5/27.
 */

public class TipsClickEvent {

    public int mGoodsId;

    public int mBrandId;

    public String mBrandName;

    public View mView;

    public TipsClickEvent(int goodsId, int brandId, String brandName, View view) {
        mGoodsId = goodsId;
        mBrandId = brandId;
        mBrandName = brandName;
        mView = view;
    }
}
