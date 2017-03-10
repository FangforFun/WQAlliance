package gkzxhn.wqalliance.mvp.model.entities;

import java.util.List;

/**
 * Author: Huang ZN
 * Date: 2017/3/8
 * Email:943852572@qq.com
 * Description:
 */

public class EvidenceList {

    /**
     * data : [{"id":1,"type":0,"evidenceName":"原告营业执照","createdAt":1488619547000,"updatedAt":null,"sysFlag":1},{"id":2,"type":0,"evidenceName":"原告法定代表人证明","createdAt":1488619581000,"updatedAt":null,"sysFlag":1},{"id":3,"type":0,"evidenceName":"被告工商登记信息","createdAt":1488619604000,"updatedAt":null,"sysFlag":1},{"id":4,"type":0,"evidenceName":"被告身份","createdAt":1488619622000,"updatedAt":null,"sysFlag":1}]
     * code : 0
     * msg : 查询需上传的证据列表成功
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * type : 0
         * evidenceName : 原告营业执照
         * createdAt : 1488619547000
         * updatedAt : null
         * sysFlag : 1
         */

        public int id;
        public int type;
        public String evidenceName;
        public long createdAt;
        public String updatedAt;
        public int sysFlag;
    }
}
