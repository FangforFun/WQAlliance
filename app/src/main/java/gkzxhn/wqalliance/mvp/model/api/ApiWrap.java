package gkzxhn.wqalliance.mvp.model.api;

import java.io.File;
import java.util.Map;

import gkzxhn.wqalliance.mvp.model.api.service.CommonService;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Code;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.model.entities.OrderProcedure;
import gkzxhn.wqalliance.mvp.model.entities.OrderResult;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.ScanningInfo;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import gkzxhn.wqalliance.mvp.model.entities.VersionBean;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Huang ZN
 * Date: 2017/3/7
 * Email:943852572@qq.com
 * Description:
 */

public class ApiWrap {

    /**
     * 注册
     * @param phone
     * @param pwd
     * @param code
     * @param subscriber
     */
    public static void register(String phone, String pwd, String code, SimpleObserver<Result> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.register(phone, pwd, code).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 登录
     * @param phone
     * @param pwd
     * @param subscriber
     */
    public static void login(String phone, String pwd, SimpleObserver<Result> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.login(phone, pwd).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 忘记密码
     * @param phone
     * @param pwd
     * @param code
     * @param subscriber
     */
    public static void forgetPassword(String phone, String pwd, String code, SimpleObserver<Result> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.forgetPassword(phone, pwd, code).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     * @param phone
     * @param oldPwd
     * @param newPwd
     * @param subscriber
     */
    public static void updatePassword(String phone, String oldPwd, String newPwd, SimpleObserver<Result> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.updatePassword(phone, oldPwd, newPwd).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改资料
     * @param map
     * @param subscriber
     */
    public static void updateUserInfo(Map<String, Object> map, SimpleObserver<Result> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.updateUser(map).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查询需上传的证据列表
     * @param type
     * @param subscriber
     */
    public static void getEvidences(int type, SimpleObserver<EvidenceList> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.getEvidences(type).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取订单流程
     * @param userId
     * @param orderId
     * @param subscriber
     */
    public static void getOrderProcedure(int userId, int orderId, SimpleObserver<OrderProcedure> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.getOrderProcedure(userId, orderId).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查询订单
     * @param userId
     * @param orderStatus
     * @param subscriber
     * TODO ...
     */
    public static void getOrders(int userId, int orderStatus, SimpleObserver<OrderResult> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.getOrders(userId, orderStatus).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 客户提交签约
     * @param userId
     * @param companyName
     * @param trademarkImgUrl
     * @param propertyImgUrl
     * @param signedType
     * @param subscriber
     */
    public static void submitUserSign(int userId, String companyName, String trademarkImgUrl, String propertyImgUrl, int signedType, SimpleObserver<Result> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.submitUserSign(userId, companyName, trademarkImgUrl, propertyImgUrl, signedType).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查询客户信息
     * @param userId
     * @param subscriber
     */
    public static void getUser(int userId, SimpleObserver<Result> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.getUser(userId).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发送验证码
     * @param phone
     * @param subscriber
     */
    public static void sendMsg(String phone, SimpleObserver<ResponseBody> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.sendMsg(phone).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传图片
     * @param filePath
     * @param fileName
     * @param subscriber
     */
    public static void uploadImage(String filePath, String fileName,
                                   SimpleObserver<UploadImageResult> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        File file = new File(filePath, fileName);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("photots", fileName, body);
        service.upLoadImage(part).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提交订单
     * @param userId
     * @param title
     * @param description
     * @param orderEvidenceJs
     * @param subscriber
     */
    public static void addOrder(int userId, String title, String description, String orderEvidenceJs, SimpleObserver<Result> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.addOrder(userId, title, description, orderEvidenceJs).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 版本更新
     * @param subscriber
     */
    public static void versionUpdate(SimpleObserver<VersionBean> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.versionUpdate(2).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 扫码获得商品信息
     * @param code
     * @param subscriber
     */
    public static void getGoodsByCode(String code, SimpleObserver<ScanningInfo> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.getGoodsByCode(code).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提交打假内容
     * @param goodsName
     * @param imgUrl
     * @param address
     * @param subscriber
     */
    public static void fightFake(String goodsName, String imgUrl, String address, SimpleObserver<Code> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.fake(goodsName, imgUrl, address).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
