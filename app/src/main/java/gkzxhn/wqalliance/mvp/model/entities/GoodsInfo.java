package gkzxhn.wqalliance.mvp.model.entities;

import java.util.List;

/**
 * Created by 方 on 2017/5/31.
 */

public class GoodsInfo {

    /**
     * data : [{"id":2,"brandId":5,"goodsName":"拉菲干红尚品波尔多AOC葡萄酒","imgUrl":"https://www.weiquanlianmeng.com/img/1489734306393.jpg","createdAt":1495865995000,"updatedAt":null,"sysFlag":1,"goodsDesc":"净含量：500ml\r\n厂名：拉菲酒庄有限公司"},{"id":3,"brandId":5,"goodsName":"法国路易拉菲","imgUrl":"https://www.weiquanlianmeng.com/img/1489734306393.jpg","createdAt":1495866157000,"updatedAt":null,"sysFlag":1,"goodsDesc":"净含量：500ml\r\n厂名：拉菲酒庄有限公司"}]
     * code : 0
     * msg : 查询商品成功
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 2
         * brandId : 5
         * goodsName : 拉菲干红尚品波尔多AOC葡萄酒
         * imgUrl : https://www.weiquanlianmeng.com/img/1489734306393.jpg
         * createdAt : 1495865995000
         * updatedAt : null
         * sysFlag : 1
         * goodsDesc : 净含量：500ml
         厂名：拉菲酒庄有限公司
         */

        public int id;
        public int brandId;
        public String goodsName;
        public String imgUrl;
        public long createdAt;
        public Object updatedAt;
        public int sysFlag;
        public String goodsDesc;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", brandId=" + brandId +
                    ", goodsName='" + goodsName + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    ", sysFlag=" + sysFlag +
                    ", goodsDesc='" + goodsDesc + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
