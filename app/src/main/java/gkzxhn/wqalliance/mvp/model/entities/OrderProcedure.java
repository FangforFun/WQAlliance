package gkzxhn.wqalliance.mvp.model.entities;

import java.util.List;

/**
 * Author: Huang ZN
 * Date: 2017/3/10
 * Email:943852572@qq.com
 * Description:
 */

public class OrderProcedure {


    /**
     * msg : 查询客户订单成功
     * code : 0
     * data : {"orderProcedures":[{"id":27,"orderId":4,"type":3,"notes":"xxx","createdAt":1489198115000,"sysFlag":1}],"lawyer":{"id":2,"name":"张三","phone":"18163657553","password":"E10ADC3949BA59ABBE56E057F20F883E","faceImgUrl":"http://skks.jpg","email":"929183@qq.com","area":"湖南长沙1","occupationNo":"iowuw80128312","lawfirmName":"长沙某某律师社","createdAt":1489039472000,"updatedAt":1489202114000,"sysFlag":1,"yxAccid":"2d4b","yxToken":"1bf370483bfd4245eb193653cad9af83"},"order":{"id":4,"userId":2,"lawyerId":2,"title":"邋遢塔","money":0,"orderType":0,"orderStatus":2,"createdAt":1489137437000,"updatedAt":1489144346000,"sysFlag":1,"description":"xxx"}}
     */

    private String msg;
    private int code;
    /**
     * orderProcedures : [{"id":27,"orderId":4,"type":3,"notes":"xxx","createdAt":1489198115000,"sysFlag":1}]
     * lawyer : {"id":2,"name":"张三","phone":"18163657553","password":"E10ADC3949BA59ABBE56E057F20F883E","faceImgUrl":"http://skks.jpg","email":"929183@qq.com","area":"湖南长沙1","occupationNo":"iowuw80128312","lawfirmName":"长沙某某律师社","createdAt":1489039472000,"updatedAt":1489202114000,"sysFlag":1,"yxAccid":"2d4b","yxToken":"1bf370483bfd4245eb193653cad9af83"}
     * order : {"id":4,"userId":2,"lawyerId":2,"title":"邋遢塔","money":0,"orderType":0,"orderStatus":2,"createdAt":1489137437000,"updatedAt":1489144346000,"sysFlag":1,"description":"xxx"}
     */

    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderProcedure{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data.toString() +
                '}';
    }

    public static class DataBean {
        /**
         * id : 2
         * name : 张三
         * phone : 18163657553
         * password : E10ADC3949BA59ABBE56E057F20F883E
         * faceImgUrl : http://skks.jpg
         * email : 929183@qq.com
         * area : 湖南长沙1
         * occupationNo : iowuw80128312
         * lawfirmName : 长沙某某律师社
         * createdAt : 1489039472000
         * updatedAt : 1489202114000
         * sysFlag : 1
         * yxAccid : 2d4b
         * yxToken : 1bf370483bfd4245eb193653cad9af83
         */

        private LawyerBean lawyer;
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
         * id : 27
         * orderId : 4
         * type : 3
         * notes : xxx
         * createdAt : 1489198115000
         * sysFlag : 1
         */

        private List<OrderProceduresBean> orderProcedures;

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

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

        @Override
        public String toString() {
            return "DataBean{" +
                    "lawyer=" + lawyer.toString() +
                    ", order=" + order.toString() +
                    ", orderProcedures=" + orderProcedures.size() +
                    '}';
        }

        public static class LawyerBean {

            @Override
            public String toString() {
                return "LawyerBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", phone='" + phone + '\'' +
                        ", password='" + password + '\'' +
                        ", faceImgUrl='" + faceImgUrl + '\'' +
                        ", email='" + email + '\'' +
                        ", area='" + area + '\'' +
                        ", occupationNo='" + occupationNo + '\'' +
                        ", lawfirmName='" + lawfirmName + '\'' +
                        ", createdAt=" + createdAt +
                        ", updatedAt=" + updatedAt +
                        ", sysFlag=" + sysFlag +
                        ", yxAccid='" + yxAccid + '\'' +
                        ", yxToken='" + yxToken + '\'' +
                        '}';
            }

            private int id;
            private String name;
            private String phone;
            private String password;
            private String faceImgUrl;
            private String email;
            private String area;
            private String occupationNo;
            private String lawfirmName;
            private long createdAt;
            private long updatedAt;
            private int sysFlag;
            private String yxAccid;
            private String yxToken;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getFaceImgUrl() {
                return faceImgUrl;
            }

            public void setFaceImgUrl(String faceImgUrl) {
                this.faceImgUrl = faceImgUrl;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getOccupationNo() {
                return occupationNo;
            }

            public void setOccupationNo(String occupationNo) {
                this.occupationNo = occupationNo;
            }

            public String getLawfirmName() {
                return lawfirmName;
            }

            public void setLawfirmName(String lawfirmName) {
                this.lawfirmName = lawfirmName;
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

            public String getYxAccid() {
                return yxAccid;
            }

            public void setYxAccid(String yxAccid) {
                this.yxAccid = yxAccid;
            }

            public String getYxToken() {
                return yxToken;
            }

            public void setYxToken(String yxToken) {
                this.yxToken = yxToken;
            }
        }

        public static class OrderBean {
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
    }
}
