package gkzxhn.wqalliance.mvp.model.api.service;

import java.util.Map;

import gkzxhn.wqalliance.mvp.model.entities.Code;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.model.entities.OrderProcedure;
import gkzxhn.wqalliance.mvp.model.entities.OrderResult;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.ScanningInfo;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import gkzxhn.wqalliance.mvp.model.entities.VersionBean;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 存放一些通用的API
 * Created by 方 on 2017/3/2.
 */
public interface CommonService {

    /**
     * 注册
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<Result> register(
            @Field("phone") String phone,
            @Field("password") String pwd,
            @Field("code") String code
    );

    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<Result> login(
            @Field("phone") String phone,
            @Field("password") String pwd
    );

    /**
     * 忘记密码
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/forgetPassword")
    Observable<Result> forgetPassword(@Field("phone") String phone, @Field("password") String pwd,
                @Field("code") String code
    );

    /**
     * 修改密码
     * @param phone
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/updatePassword")
    Observable<Result> updatePassword(
            @Field("phone") String phone,
            @Field("oldPassword") String oldPwd,
            @Field("password") String newPwd
    );


    /**
     * 查询客户信息
     * @param userId
     * @return
     */
    @GET("user/getUser")
    Observable<Result> getUser(
            @Query("userId") int userId
    );


    /**
     * 查询需要上传的证据列表
     * @param type
     * @return
     */
    @GET("evidence/getEvidences")
    Observable<EvidenceList> getEvidences(
            @Query("type") int type
    );

    /**
     * 查询订单
     * @param userId
     * @param orderStatus
     * @return
     */
    @FormUrlEncoded
    @POST("order/getOrders")
    Observable<OrderResult> getOrders(
        @Field("userId") Integer userId,
        @Field("orderStatus") Integer orderStatus
    );

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("yxmsg/sendMsg")
    Observable<ResponseBody> sendMsg(
            @Field("phone") String phone
    );

    /**
     * 修改资料
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/updateUser")
    Observable<Result> updateUser(
            @FieldMap Map<String, Object> map
    );

    /**
     * 客户提交签约
     * @param userId
     * @param companyName
     * @param trademarkImgUrl
     *@param propertyImgUrl  @return
     * @param signedType
     */
    @FormUrlEncoded
    @POST("userSign/submitUserSign")
    Observable<Result> submitUserSign(
            @Field("userId") int userId,
            @Field("companyName") String companyName,
            @Field("trademarkImgUrl") String trademarkImgUrl,
            @Field("propertyImgUrl") String propertyImgUrl,
            @Field("signedType") int signedType   //签约方式 : 0 线上  1 线下
    );

    /**
     * 上传图片
     * @param photo
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<UploadImageResult> upLoadImage(
            @Part MultipartBody.Part photo
    );

    /**
     * 获取订单流程
     * @param userId
     * @param orderId
     * @return
     */
    @GET("order/getOrderProcedure")
    Observable<OrderProcedure> getOrderProcedure(
            @Query("userId") int userId,
            @Query("orderId") int orderId
    );

    /**
     * 提交订单
     * @param userId
     * @param title
     * @param description
     * @param orderEvidenceJson
     * @return
     */
    @FormUrlEncoded
    @POST("order/addOrder")
    Observable<Result> addOrder(
            @Field("userId") Integer userId,
            @Field("title") String title,
            @Field("description") String description,
            @Field("orderEvidenceJson")String orderEvidenceJson
    );

    /**
     * 版本更新
     * @param appFlag
     * @return
     */
    @GET("appVersion/versionUpdate")
    Observable<VersionBean> versionUpdate(
            @Query("appFlag") int appFlag
    );

    /**
     * 二维码/条码查询商品
     * @param code
     * @return
     */
    @GET("goods/getGoodsByCode")
    Observable<ScanningInfo> getGoodsByCode(
            @Query("code") String code
    );

    @FormUrlEncoded
    @POST("goods/fake")
    Observable<Code> fake(
            @Field("goodsName") String goodsName,
            @Field("imgUrl") String imgUrl,
            @Field("address") String address
    );
}
