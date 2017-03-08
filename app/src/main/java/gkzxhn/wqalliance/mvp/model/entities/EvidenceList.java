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
     * data : [{"id":5,"type":1,"evidenceName":"1959928号商标注册","createdAt":1488619651000,"updatedAt":"1488619651000ll","sysFlag":1}]
     * code : 0
     * msg : 查询需上传的证据列表成功
     */

    private int code;
    private String msg;
    /**
     * id : 5
     * type : 1
     * evidenceName : 1959928号商标注册
     * createdAt : 1488619651000
     * updatedAt : 1488619651000ll
     * sysFlag : 1
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private int type;
        private String evidenceName;
        private long createdAt;
        private String updatedAt;
        private int sysFlag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getEvidenceName() {
            return evidenceName;
        }

        public void setEvidenceName(String evidenceName) {
            this.evidenceName = evidenceName;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getSysFlag() {
            return sysFlag;
        }

        public void setSysFlag(int sysFlag) {
            this.sysFlag = sysFlag;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", type=" + type +
                    ", evidenceName='" + evidenceName + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", sysFlag=" + sysFlag +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EvidenceList{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data.size=" + data.size() +
                '}';
    }
}
