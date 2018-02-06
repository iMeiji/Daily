package com.meiji.daily.data.remote

import com.meiji.daily.bean.PostsContentBean
import com.meiji.daily.bean.PostsListBean
import com.meiji.daily.bean.ZhuanlanBean

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Meiji on 2016/11/16.
 */

interface IApi {

    /**
     * 获取专栏信息 Retrofit + RxJava
     * https://zhuanlan.zhihu.com/api/columns/design
     *
     * @param slug
     * @return
     */
    @GET("api/columns/{slug}")
    fun getZhuanlanBean(@Path("slug") slug: String): Maybe<ZhuanlanBean>

    /**
     * 获取专栏文章 Retrofit + RxJava
     * https://zhuanlan.zhihu.com/api/columns/design/posts?limit=10&offset=10
     *
     * @param slug   专栏ID
     * @param offset 偏移量
     * @return
     */
    @GET("api/columns/{slug}/posts?limit=10")
    fun getPostsList(
            @Path("slug") slug: String,
            @Query("offset") offset: Int): Maybe<List<PostsListBean>>

    /**
     * 获取文章内容 Retrofit + RxJava
     * https://zhuanlan.zhihu.com/api/posts/25982605
     *
     * @param slug 文章ID
     * @return
     */
    @GET("api/posts/{slug}")
    fun getPostsContentBean(@Path("slug") slug: String): Maybe<PostsContentBean>

    companion object {

        const val COLUMN_URL = "https://zhuanlan.zhihu.com/api/columns/"

        const val POST_URL = "https://zhuanlan.zhihu.com/api/posts/"

        const val API_BASE = "https://zhuanlan.zhihu.com/"
    }
}
