package gkzxhn.wqalliance.mvp.model.entities;

/**
 * Created by 方 on 2017/4/27.
 */

public class ScanningInfo {

    /**
     * data : {"id":1,"goodsName":"iphone6s plus","createdAt":1493263789000,"updatedAt":null,"sysFlag":1}
     * code : 0
     * msg : 根据条形码查询商品成功
     */

    public DataBean data;
    public int code;
    public String msg;

    public static class DataBean {
        /**
         * id : 1
         * goodsName : iphone6s plus
         * createdAt : 1493263789000
         * updatedAt : null
         * sysFlag : 1
         */

        public int id;
        public String goodsName;
        public long createdAt;
        public Object updatedAt;
        public int sysFlag;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", goodsName='" + goodsName + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    ", sysFlag=" + sysFlag +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ScanningInfo{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
