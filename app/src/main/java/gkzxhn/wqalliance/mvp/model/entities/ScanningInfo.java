package gkzxhn.wqalliance.mvp.model.entities;

/**
 * Created by 方 on 2017/4/27.
 */

public class ScanningInfo {


    /**
     * data : {"goods":{"id":1,"goodsName":"五粮液1618","imgUrl":"https://www.weiquanlianmeng.com/img/1489734306393.","createdAt":1493263789000,"updatedAt":null,"sysFlag":1,"goodsDesc":"品牌：五粮液\r\n净含量：500ml\r\n厂名：宜宾五粮股份有限公司"},"scanNumber":1}
     * code : 0
     * msg : 根据条形码查询商品成功
     */

    public DataBean data;
    public int code;
    public String msg;
    public String scanningCode;

    public static class DataBean {
        /**
         * goods : {"id":1,"goodsName":"五粮液1618","imgUrl":"https://www.weiquanlianmeng.com/img/1489734306393.","createdAt":1493263789000,"updatedAt":null,"sysFlag":1,"goodsDesc":"品牌：五粮液\r\n净含量：500ml\r\n厂名：宜宾五粮股份有限公司"}
         * scanNumber : 1
         */

        public GoodsBean goods;
        public int scanNumber;

        @Override
        public String toString() {
            return "DataBean{" +
                    "goods=" + goods +
                    ", scanNumber=" + scanNumber +
                    '}';
        }

        public static class GoodsBean {
            /**
             * id : 1
             * goodsName : 五粮液1618
             * imgUrl : https://www.weiquanlianmeng.com/img/1489734306393.
             * createdAt : 1493263789000
             * updatedAt : null
             * sysFlag : 1
             * goodsDesc : 品牌：五粮液
             净含量：500ml
             厂名：宜宾五粮股份有限公司
             */

            public int id;
            public String goodsName;        //商品名
            public String imgUrl;
            public long createdAt;
            public Object updatedAt;
            public int sysFlag;
            public String goodsDesc;

            @Override
            public String toString() {
                return "GoodsBean{" +
                        "id=" + id +
                        ", goodsName='" + goodsName + '\'' +
                        ", imgUrl='" + imgUrl + '\'' +
                        ", createdAt=" + createdAt +
                        ", updatedAt=" + updatedAt +
                        ", sysFlag=" + sysFlag +
                        ", goodsDesc='" + goodsDesc + '\'' +
                        '}';
            }
        }
    }
}
