package com.meiji.daily.mvp.postscontent;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.PostsContentBean;
import com.meiji.daily.utils.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Meiji on 2016/11/23.
 */
@Deprecated
class PostsContentModel implements IPostsContent.Model {

    private static final String TAG = "PostsContentModel";
    private PostsContentBean bean;
    private Call<PostsContentBean> call;

    @Override
    public boolean retrofitRequest(int slug) {
        boolean flag = false;
        Api api = RetrofitFactory.getRetrofit().create(Api.class);
        call = api.getPostsContentBean(slug);
        try {
            Response<PostsContentBean> response = call.execute();
            if (response.isSuccessful()) {
                flag = true;
                bean = response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getContent() {

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + bean.getContent()
                + "</body>\n</html>";

        return html;
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }
}
