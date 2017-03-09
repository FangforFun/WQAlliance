package gkzxhn.wqalliance.mvp.model.api;

import java.io.File;
import java.util.Map;

import gkzxhn.wqalliance.mvp.model.api.service.CommonService;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
     * @param subscriber
     */
    public static void register(String phone, String pwd, SimpleObserver<Result> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.register(phone, pwd).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
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
     * @param subscriber
     */
    public static void forgetPassword(String phone, String pwd, SimpleObserver<Result> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonService service = retrofit.create(CommonService.class);
        service.forgetPassword(phone, pwd).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
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
}
