package gkzxhn.wqalliance.mvp.model.entities;

/**
 * Author: Huang ZN
 * Date: 2017/3/7
 * Email:943852572@qq.com
 * Description:
 */

public class Result {

    /**
     * id : 1
     * password : 96E79218965EB72C92A549DD5A330112
     * phone : 13296721646
     * faceImgUrl : xxx
     * contactNumber : xxx
     * email : xxx
     * address : xxx
     * signedStatus : 0
     * createdAt : 1488858898000
     * updatedAt : 1488858898000
     * sysFlag : 1
     * yxAccess : tesstAccess
     * yxToken : testToken
     */

    private DataBean data;
    /**
     * data : {"id":1,"password":"96E79218965EB72C92A549DD5A330112","phone":"13296721646","faceImgUrl":"xxx","contactNumber":"xxx","email":"xxx","address":"xxx","signedStatus":0,"createdAt":1488858898000,"updatedAt":1488858898000,"sysFlag":1,"yxAccess":"tesstAccess","yxToken":"testToken"}
     * code : 0
     * msg : 注册成功
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
        private int id;
        private String password;
        private String phone;
        private String faceImgUrl;
        private String contactNumber;
        private String email;
        private String address;
        private int signedStatus;
        private long createdAt;
        private long updatedAt;
        private int sysFlag;
        private String yxAccess;
        private String yxToken;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFaceImgUrl() {
            return faceImgUrl;
        }

        public void setFaceImgUrl(String faceImgUrl) {
            this.faceImgUrl = faceImgUrl;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getSignedStatus() {
            return signedStatus;
        }

        public void setSignedStatus(int signedStatus) {
            this.signedStatus = signedStatus;
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

        public String getYxAccess() {
            return yxAccess;
        }

        public void setYxAccess(String yxAccess) {
            this.yxAccess = yxAccess;
        }

        public String getYxToken() {
            return yxToken;
        }

        public void setYxToken(String yxToken) {
            this.yxToken = yxToken;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    ", faceImgUrl='" + faceImgUrl + '\'' +
                    ", contactNumber='" + contactNumber + '\'' +
                    ", email='" + email + '\'' +
                    ", address='" + address + '\'' +
                    ", signedStatus=" + signedStatus +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    ", sysFlag=" + sysFlag +
                    ", yxAccess='" + yxAccess + '\'' +
                    ", yxToken='" + yxToken + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data.toString() +
                ", code=" + code +
                ", msg='" + msg+ '\'' +
                '}';
    }
}
