package gkzxhn.wqalliance.mvp.model.entities;

import java.util.List;

/**
 * Author: Huang ZN
 * Date: 2017/3/10
 * Email:943852572@qq.com
 * Description:
 */

public class OrdeProcedure {

    /**
     * order : {"id":4,"userId":2,"lawyerId":2,"title":"邋遢塔","money":0,"orderType":0,"orderStatus":2,"createdAt":1489137437000,"updatedAt":1489144346000,"sysFlag":1,"description":"xxx"}
     * orderProcedures : [{"id":1,"orderId":1,"type":3,"notes":"xxx","createdAt":1489045917000,"sysFlag":1},{"id":2,"orderId":1,"type":3,"notes":"xxx","createdAt":1489046382000,"sysFlag":1},{"id":3,"orderId":1,"type":3,"notes":"xxx","createdAt":1489048061000,"sysFlag":1},{"id":4,"orderId":2,"type":2,"notes":"xxx","createdAt":1489129019000,"sysFlag":1},{"id":5,"orderId":3,"type":0,"notes":"xxx","createdAt":1489136479000,"sysFlag":1},{"id":6,"orderId":4,"type":0,"notes":"xxx","createdAt":1489137438000,"sysFlag":1},{"id":7,"orderId":4,"type":0,"notes":"xxx","createdAt":1489137836000,"sysFlag":1},{"id":8,"orderId":4,"type":0,"notes":"xxx","createdAt":1489138045000,"sysFlag":1},{"id":9,"orderId":7,"type":0,"notes":"xxx","createdAt":1489139886000,"sysFlag":1},{"id":10,"orderId":7,"type":0,"notes":"xxx","createdAt":1489139903000,"sysFlag":1},{"id":11,"orderId":7,"type":0,"notes":"xxx","createdAt":1489139983000,"sysFlag":1},{"id":12,"orderId":3,"type":2,"notes":"xxx","createdAt":1489144844000,"sysFlag":1},{"id":13,"orderId":4,"type":2,"notes":"xxx","createdAt":1489144856000,"sysFlag":1},{"id":14,"orderId":5,"type":2,"notes":"xxx","createdAt":1489144901000,"sysFlag":1},{"id":15,"orderId":6,"type":2,"notes":"xxx","createdAt":1489144923000,"sysFlag":1}]
     */

    private DataBean data;
    /**
     * data : {"order":{"id":4,"userId":2,"lawyerId":2,"title":"邋遢塔","money":0,"orderType":0,"orderStatus":2,"createdAt":1489137437000,"updatedAt":1489144346000,"sysFlag":1,"description":"xxx"},"orderProcedures":[{"id":1,"orderId":1,"type":3,"notes":"xxx","createdAt":1489045917000,"sysFlag":1},{"id":2,"orderId":1,"type":3,"notes":"xxx","createdAt":1489046382000,"sysFlag":1},{"id":3,"orderId":1,"type":3,"notes":"xxx","createdAt":1489048061000,"sysFlag":1},{"id":4,"orderId":2,"type":2,"notes":"xxx","createdAt":1489129019000,"sysFlag":1},{"id":5,"orderId":3,"type":0,"notes":"xxx","createdAt":1489136479000,"sysFlag":1},{"id":6,"orderId":4,"type":0,"notes":"xxx","createdAt":1489137438000,"sysFlag":1},{"id":7,"orderId":4,"type":0,"notes":"xxx","createdAt":1489137836000,"sysFlag":1},{"id":8,"orderId":4,"type":0,"notes":"xxx","createdAt":1489138045000,"sysFlag":1},{"id":9,"orderId":7,"type":0,"notes":"xxx","createdAt":1489139886000,"sysFlag":1},{"id":10,"orderId":7,"type":0,"notes":"xxx","createdAt":1489139903000,"sysFlag":1},{"id":11,"orderId":7,"type":0,"notes":"xxx","createdAt":1489139983000,"sysFlag":1},{"id":12,"orderId":3,"type":2,"notes":"xxx","createdAt":1489144844000,"sysFlag":1},{"id":13,"orderId":4,"type":2,"notes":"xxx","createdAt":1489144856000,"sysFlag":1},{"id":14,"orderId":5,"type":2,"notes":"xxx","createdAt":1489144901000,"sysFlag":1},{"id":15,"orderId":6,"type":2,"notes":"xxx","createdAt":1489144923000,"sysFlag":1}]}
     * code : 0
     * msg : 查询客户订单成功
     */

    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * id : 4
         * userId : 2
         * lawyerId : 2
         * title : 邋遢塔
         * money : 0
         * orderType : 0
         * orderStatus : 2
         * createdAt : 1489137437000
         * updatedAt : 1489144346000
         * sysFlag : 1
         * description : xxx
         */

        private OrderBean order;
        /**
         * id : 1
         * orderId : 1
         * type : 3
         * notes : xxx
         * createdAt : 1489045917000
         * sysFlag : 1
         */

        private List<OrderProceduresBean> orderProcedures;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<OrderProceduresBean> getOrderProcedures() {
            return orderProcedures;
        }

        public void setOrderProcedures(List<OrderProceduresBean> orderProcedures) {
            this.orderProcedures = orderProcedures;
        }

        public static class OrderBean {

            @Override
            public String toString() {
                return "OrderBean{" +
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
        }

        public static class OrderProceduresBean {
            private int id;
            private int orderId;
            private int type;
            private String notes;
            private long createdAt;
            private int sysFlag;

            @Override
            public String toString() {
                return "OrderProceduresBean{" +
                        "id=" + id +
                        ", orderId=" + orderId +
                        ", type=" + type +
                        ", notes='" + notes + '\'' +
                        ", createdAt=" + createdAt +
                        ", sysFlag=" + sysFlag +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public long getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(long createdAt) {
                this.createdAt = createdAt;
            }

            public int getSysFlag() {
                return sysFlag;
            }

            public void setSysFlag(int sysFlag) {
                this.sysFlag = sysFlag;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "order=" + order.toString() +
                    ", orderProcedures=" + orderProcedures.size() +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrdeProcedure{" +
                "data=" + data.toString() +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
