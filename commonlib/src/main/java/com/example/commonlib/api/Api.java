package com.example.commonlib.api;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.CommentDetailGson;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.ExpressGson;
import com.example.commonlib.gson.ExpressTraceGson;
import com.example.commonlib.gson.GoodsCommentGson;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.GoodsShareGson;
import com.example.commonlib.gson.GoodsStyleGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.commonlib.gson.MemberGson;
import com.example.commonlib.gson.NewestShelfGson;
import com.example.commonlib.gson.OrderDetailGson;
import com.example.commonlib.gson.SecondHandsGoodsGson;
import com.example.commonlib.gson.SecondKindGson;
import com.example.commonlib.gson.ShopCarGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.gson.SubmitOrderItemGson;
import com.example.commonlib.gson.SystemMessageGson;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.gson.UserPaymentGson;
import com.example.commonlib.gson.UserReceiveAddressGson;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    @GET("/StuShop/public/index.php/index/User/getUserList")
    Observable<BaseGson<UserGson>> getUserList();


    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/User/userLoginByUserName")
    Observable<BaseGson<UserGson>> userLoginByUserName(@Field("username") String username, @Field("password") String password);

    //获取商品种类
    @GET("/StuShop/public/index.php/index/Goods/getGoodsKindList")
    Observable<BaseGson<KindItemGson>> getGoodsList();

    @GET("/StuShop/public/index.php/index/Express/queryExpressByNum")
    Observable<BaseGson<ExpressTraceGson>> queryExpressByNum(@Query("expressNum") String expressNum);

    //通过商品id查询商品列表
    @GET("/StuShop/public/index.php/index/Goods/getGoodsListByPid")
    Observable<BaseGson<KindItemGson>> getGoodsListByPid(@Query("pid") String pid);


    @GET("/StuShop/public/index.php/index/Goods/queryPurseGoods")
    Observable<BaseGson<GoodsGson>> queryPurseGoods(@Query("isPurse") String isPurse);


    @GET("/StuShop/public/index.php/index/Goods/getHomeActivity")
    Observable<BaseGson<HotPurseActivityGson>> getHomeActivity();

    @GET("/StuShop/public/index.php/index/Goods/queryTimeSell")
    Observable<BaseGson<TimeGoodsGson.TimeBean>> queryTimeSell();

    //获取主页轮播图
    @GET("/StuShop/public/index.php/index/Banner/getHomeBanner")
    Observable<BaseGson<BannerGson>> getHomeBanner();

    @GET("/StuShop/public/index.php/index/Goods/getGoodsDetailById")
    Observable<BaseGson<GoodsDetailGson>> getGoodsDetailById(@Query("goodsId") String goodsId, @Query("userId") String userId);

    @GET("/StuShop/public/index.php/index/Dryinglist/getGoodsShareList")
    Observable<BaseGson<GoodsShareGson>> getGoodsShareList(@Query("type") String type, @Query("page") String page);

    @GET("/StuShop/public/index.php/index/Goods/getGoodsListByKind")
    Observable<BaseGson<GoodsGson>> getGoodsListByKind(@Query("kind") String kind, @Query("type") String type, @Query("isasc") String isasc);


    @GET("/StuShop/public/index.php/index/Banner/getMarqueenList")
    Observable<BaseGson<MarQueenGson>> getMarqueenList();

    @GET("/StuShop/public/index.php/index/Goods/queryGoodsStyle")
    Observable<BaseGson<GoodsStyleGson>> queryGoodsStyle(@Query("goodsId") String goodId);

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/Goods/addGoodsInShopCarById")
    Observable<BaseGson<EmptyGson>> addGoodsInShopCarById(@Field("userId") String userId, @Field("count") String count, @Field("goodsId") String goodsId, @Field("type") String type);

    //    @FormUrlEncoded
    @Multipart
    @POST("/StuShop/public/index.php/index/User/userRegister")
    Observable<BaseGson<UserGson>> userRegister(@PartMap() Map<String, RequestBody> partMap,
                                                @Part MultipartBody.Part avatar
    );


    @GET("/StuShop/public/index.php/index/Goods/queryUserShopCarByUid")
    Observable<BaseGson<ShopCarGson>> queryUserShopCarByUid(@Query("userId") String goodId);

    @GET("/StuShop/public/index.php/index/User/queryUserReceiveAddress")
    Observable<BaseGson<UserReceiveAddressGson>> queryUserUserAddress(@Query("userId") String goodId);

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/User/saveNewAddressByUserId")
    Observable<BaseGson<EmptyGson>> saveNewAddressByUserId(@Field("username") String username,
                                                           @Field("tel") String tel,
                                                           @Field("local") String local,
                                                           @Field("detail") String detail,
                                                           @Field("default") String defaults,
                                                           @Field("userId") String userId
    );

    @GET("/StuShop/public/index.php/index/User/queryUserCouponList")
    Observable<BaseGson<CouponGson>> queryUserCouponList(@Query("userId") String goodId);

    @GET("/StuShop/public/index.php/index/Express/queryAllExpress")
    Observable<BaseGson<ExpressGson>> queryAllExpress();

    @GET("/StuShop/public/index.php/index/User/queryUserOrderByStatus")
    Observable<BaseGson<List<UserOrderStatusGson>>> queryUserOrderByStatus(@Query("userId") String userId, @Query("status") String status);

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/Goods/submitUserOrder")
    Observable<BaseGson<SubmitOrderItemGson>> submitUserOrder(@Field("userId") String userId, @Field("goodsId") String goodsId);

    @GET("/StuShop/public/index.php/index/User/confirmationOrderByUserId")
    Observable<BaseGson<OrderDetailGson>> confirmationOrderByUserId(@Query("userId") String userId, @Query("goodsId") String goodsId, @Query("orderNum") String orderNum);

    @GET("/StuShop/public/index.php/index/User/deleteOrderByOrderNum")
    Observable<BaseGson<EmptyGson>> deleteOrderByOrderNum(@Query("orderNum") String orderNum, @Query("userId") String userId);

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/Goods/insertUserOrder")
    Observable<BaseGson<SubmitOrderGson>> insertUserOrder(@Field("userId") String userId, @Field("goodsId") String goodsId);

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/Goods/addUserCollection")
    Observable<BaseGson<EmptyGson>> addUserCollection(@Field("userId") String userId, @Field("goodsId") String goodsId, @Field("isDelete") String isDelete);


    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/User/submitOrderByUserId")
    Observable<BaseGson<EmptyGson>> submitOrderByUserId(@Field("userId") String userId, @Field("address") String address, @Field("goodsId") String goodsId, @Field("couponId") String couponId, @Field("orderNum") String orderNum, @Field("userToken") String userToken, @Field("message") String message, @Field("expressId") String expressId);

    @GET("/StuShop/public/index.php/index/User/queryUserAccount")
    Observable<BaseGson<EmptyGson>> queryUserAccount(@Query("username") String username);

    @GET("/StuShop/public/index.php/index/Goods/queryGoodsComment")
    Observable<BaseGson<GoodsCommentGson>> queryGoodsComment(@Query("goodsId") String goodsId);


    @GET("/StuShop/public/index.php/index/User/queryUserOrderCount")
    Observable<BaseGson<UserPaymentGson>> queryUserOrderCount(@Query("userId") String userId);

    @GET("/StuShop/public/index.php/index/User/queryUserCollection")
    Observable<BaseGson<GoodsGson>> queryUserCollection(@Query("userId") String userId);

    @GET("/StuShop/public/index.php/index/Banner/queryFlashAdvertisement")
    Observable<BaseGson<AdvertisementGson>> queryFlashAdvertisement();

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/User/updateOrderStatusByReceive")
    Observable<BaseGson<EmptyGson>> updateOrderStatusByReceive(@Field("userId") String userId, @Field("orderNum") String orderNum);

    @Multipart
    @POST("/StuShop/public/index.php/index/Feedback/userFeedBack")
    Observable<BaseGson<EmptyGson>> userFeedBack(
            @PartMap() Map<String, RequestBody> partMap,
            @Part List<MultipartBody.Part> file);

    @Multipart
    @POST("/StuShop/public/index.php/index/Goods/updateGoodsEvaluate")
    Observable<BaseGson<EmptyGson>> updateGoodsEvaluate(
            @PartMap() Map<String, RequestBody> partMap,
            @Part List<MultipartBody.Part> file);


    @GET("/StuShop/public/index.php/index/Goods/newUpperShelf")
    Observable<BaseGson<NewestShelfGson>> newUpperShelf(@Query("date") String date);

    @GET("/StuShop/public/index.php/index/Second/queryAllSecondKind")
    Observable<BaseGson<SecondKindGson>> queryAllSecondKind();

    @Multipart
    @POST("/StuShop/public/index.php/index/Second/addSecondHanding")
    Observable<BaseGson<EmptyGson>> addSecondHanding(
            @PartMap() Map<String, RequestBody> partMap,
            @Part List<MultipartBody.Part> file);

    @GET("/StuShop/public/index.php/index/Second/queryAllSecondGoods")
    Observable<BaseGson<SecondHandsGoodsGson>> queryAllSecondGoods(@Query("limit") String limit, @Query("page") String page);

    @GET("/StuShop/public/index.php/index/Time/queryGoodsTime")
    Observable<BaseGson<TimeGoodsGson>> queryGoodsTime(@Query("timeId") String timeId);

    @GET("/StuShop/public/index.php/index/Dryinglist/queryGoodsCommentById")
    Observable<BaseGson<CommentDetailGson>> queryGoodsCommentById(@Query("commentId") String commentId);

    @GET("/StuShop/public/index.php/index/Goods/queryHomeActivityByName")
    Observable<BaseGson<GoodsGson>> queryHomeActivityByName(@Query("activityName") String activityName);

    @GET("/StuShop/public/index.php/index/User/queryMemberPrice")
    Observable<BaseGson<MemberGson>> queryMemberPrice();

    @GET("/StuShop/public/index.php/index/Banner/querySystemPushMessage")
    Observable<BaseGson<SystemMessageGson>> querySystemPushMessage(@Query("page") String page);

    @GET("/StuShop/public/index.php/index/Goods/queryMemberShipGoods")
    Observable<BaseGson<GoodsGson>> queryMemberShipGoods();

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/index/User/submitUserMemberShip")
    Observable<BaseGson<EmptyGson>> submitUserMemberShip(@Field("userId") String userId, @Field("memberRank") String memberRank);
}
