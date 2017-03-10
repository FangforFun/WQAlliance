package gkzxhn.wqalliance.mvp.model.api.service;

import java.util.Map;

import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import okhttp3.MultipartBody;
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
            @Field("password") String pwd
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
    Observable<Result> forgetPassword(@Field("phone") String phone, @Field("password") String pwd);

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
    Observable getOrders(
        @Field("userId") Integer userId,
        @Field("orderStatus") Integer orderStatus
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
     * @param propertyImgUrl
     * @return
     */
    @FormUrlEncoded
    @POST("userSign/submitUserSign")
    Observable<Result> submitUserSign(
            @Field("userId") int userId,
            @Field("companyName") String companyName,
            @Field("trademarkImgUrl") String trademarkImgUrl,
            @Field("propertyImgUrl") String propertyImgUrl
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
}
