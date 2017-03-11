package gkzxhn.wqalliance.mvp.model.entities;

import java.util.List;

/**
 * Author: Huang ZN
 * Date: 2017/3/10
 * Email:943852572@qq.com
 * Description:查询订单结果bean
 */

public class OrderResult {

    /**
     * data : [{"id":3,"userId":2,"lawyerId":1,"title":"钢管舞","money":0,"orderType":0,"orderStatus":0,"createdAt":1489136441000,"updatedAt":1489136441000,"sysFlag":1,"description":"xxx"}]
     * code : 0
     * msg : 查询客户订单成功
     */

    private int code;
    private String msg;
    /**
     * id : 3
     * userId : 2
     * lawyerId : 1
     * title : 钢管舞
     * money : 0
     * orderType : 0
     * orderStatus : 0
     * createdAt : 1489136441000
     * updatedAt : 1489136441000
     * sysFlag : 1
     * description : xxx
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
        private int userId;
        private int lawyerId;
        private String title;
        private int money;
        private int orderType;
        private int orderStatus;
        private long createdAt;
        private long updatedAt;
        private int sysFlag;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getLawyerId() {
            return lawyerId;
        }

        public void setLawyerId(int lawyerId) {
            this.lawyerId = lawyerId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getSysFlag() {
            return sysFlag;
        }

        public void setSysFlag(int sysFlag) {
            this.sysFlag = sysFlag;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", lawyerId=" + lawyerId +
                    ", title='" + title + '\'' +
                    ", money=" + money +
                    ", orderType=" + orderType +
                    ", orderStatus=" + orderStatus +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    ", sysFlag=" + sysFlag +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data.size() +
                '}';
    }
}