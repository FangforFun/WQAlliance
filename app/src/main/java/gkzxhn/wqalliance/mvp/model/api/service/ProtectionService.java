package gkzxhn.wqalliance.mvp.model.api.service;

import gkzxhn.wqalliance.mvp.model.entities.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 方 on 2017/3/10.
 */

public interface ProtectionService {

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
}
