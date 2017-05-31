package gkzxhn.wqalliance.mvp.model.entities;

import java.util.List;

/**
 * Created by 方 on 2017/5/27.
 */

public class BrandsInfo {
    @Override
    public String toString() {
        return "BrandsInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * data : [{"id":5,"brandName":"拉菲","createdAt":1495865466000,"updatedAt":null,"sysFlag":1},{"id":6,"brandName":"拉梦多","createdAt":1495865483000,"updatedAt":null,"sysFlag":1}]
     * code : 0
     * msg : 查询商品品牌成功
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 5
         * brandName : 拉菲
         * createdAt : 1495865466000
         * updatedAt : null
         * sysFlag : 1
         */

        public int id;
        public String brandName;
        public long createdAt;
        public Object updatedAt;
        public int sysFlag;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", brandName='" + brandName + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    ", sysFlag=" + sysFlag +
                    '}';
        }
    }
}
