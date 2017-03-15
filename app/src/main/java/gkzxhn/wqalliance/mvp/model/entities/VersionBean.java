package gkzxhn.wqalliance.mvp.model.entities;

/**
 * Created by 方 on 2017/3/14.
 */

public class VersionBean {

    /**
     * msg : 获取版本信息成功
     * code : 0
     * data : {"id":2,"versionNo":1,"versionName":"1.0.1","appFlag":2,"forceFlag":0,"sysFlag":1,"downloadUrl":"https://www.weiquanlianmeng.com/apk/WQAlliance.apk"}
     */

    public String msg;
    public int code;
    public DataBean data;

    public static class DataBean {
        /**
         * id : 2
         * versionNo : 1
         * versionName : 1.0.1
         * appFlag : 2
         * forceFlag : 0
         * sysFlag : 1
         * downloadUrl : https://www.weiquanlianmeng.com/apk/WQAlliance.apk
         */

        public int id;
        public int versionNo;
        public String versionName;
        public int appFlag;
        public int forceFlag;
        public int sysFlag;
        public String downloadUrl;
    }
}
