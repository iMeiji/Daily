package com.meiji.daily.utils;

import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.bean.ZhuanlanBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Meiji on 2016/11/16.
 */

public interface Api {
    String BASE_URL = "https://zhuanlan.zhihu.com/api/columns/";

    String POST_URL = "https://zhuanlan.zhihu.com/api/posts/";

    String API_BASE = "https://zhuanlan.zhihu.com/api/";


    /**
     * 获取专栏信息
     * https://zhuanlan.zhihu.com/api/columns/design
     *
     * @param slug 专栏ID
     * @return
     */
    @GET("columns/{slug}")
    Call<ZhuanlanBean> getZhuanlan(@Path("slug") String slug);


    /**
     * https://zhuanlan.zhihu.com/api/columns/design/posts?limit=10&offset=10
     */
    @GET("columns/{slug}/posts?limit=10")
    Call<PostsListBean> getPostsList(@Path("slug") String slug, @Query("offset") int offset);
}
