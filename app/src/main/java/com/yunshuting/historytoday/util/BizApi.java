package com.yunshuting.historytoday.util;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BizApi{
/**
 * 获取某一天的数据列表
 * @param m 月份
 * @param d 日期
 * @param limit 最大条数
 * @return
 */
    @GET("api/history/list?")
    Call<HistoryDto> getListData(@Query("m") int m, @Query("d") int d, @Query("limit") int limit);

    /**
     * 获取详情信息
     * @param  id id
     * @return
     */
    @GET("api/history/get?")
    Call<HistoryDetailDto> getDetail(@Query("id") String id);

}
