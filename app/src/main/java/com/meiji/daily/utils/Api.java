package com.meiji.daily.utils;

import com.meiji.daily.bean.PostsContentBean;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.bean.ZhuanlanBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Meiji on 2016/11/16.
 */

public interface Api {
    String COLUMN_URL = "https://zhuanlan.zhihu.com/api/columns/";

    String POST_URL = "https://zhuanlan.zhihu.com/api/posts/";

    String API_BASE = "https://zhuanlan.zhihu.com/";


    /**
     * 获取专栏信息
     * https://zhuanlan.zhihu.com/api/columns/design
     *
     * @param slug 专栏ID
     * @return
     */
    @GET("api/columns/{slug}")
    Call<ZhuanlanBean> getZhuanlanBean(@Path("slug") String slug);

    /**
     * 获取专栏文章
     * https://zhuanlan.zhihu.com/api/columns/design/posts?limit=10&offset=10
     *
     * @param slug   专栏ID
     * @param offset 偏移量
     * @return
     */
    @GET("api/columns/{slug}/posts?limit=10")
    Call<List<PostsListBean>> getPostsList(
            @Path("slug") String slug,
            @Query("offset") int offset);

    /**
     * https://zhuanlan.zhihu.com/api/posts/25982605
     *
     * @param slug 文章ID
     * @return
     */
    @GET("api/posts/{slug}")
    Call<PostsContentBean> getPostsContentBean(@Path("slug") int slug);
}
