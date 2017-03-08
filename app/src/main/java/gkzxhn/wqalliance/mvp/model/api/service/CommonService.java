package gkzxhn.wqalliance.mvp.model.api.service;

import java.util.Map;

import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * 修改密码
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/updatePassword")
    Observable<Result> updatePassword(
            @Field("phone") String phone,
            @Field("password") String pwd
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
