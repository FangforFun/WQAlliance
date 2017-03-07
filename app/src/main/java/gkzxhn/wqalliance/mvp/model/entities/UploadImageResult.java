package gkzxhn.wqalliance.mvp.model.entities;

/**
 * Author: Huang ZN
 * Date: 2017/3/7
 * Email:943852572@qq.com
 * Description:
 */

public class UploadImageResult {

    /**
     * imgUrl : http://192.168.0.13:8080/activistAlliance/img/uploadImg/1.png
     */

    private DataBean data;
    /**
     * data : {"imgUrl":"http://192.168.0.13:8080/activistAlliance/img/uploadImg/1.png"}
     * code : 0
     * msg : 图片上传成功
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
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UploadImageResult{" +
                "data=" + data.toString() +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
