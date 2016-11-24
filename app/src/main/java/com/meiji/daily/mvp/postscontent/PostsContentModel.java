package com.meiji.daily.mvp.postscontent;

import com.google.gson.Gson;
import com.meiji.daily.utils.Api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/11/23.
 */

public class PostsContentModel implements IPostsContent.Model {

    private OkHttpClient client = new OkHttpClient();
    private Call call;
    private Gson gson = new Gson();
    private PostsContentBean bean;

    @Override
    public boolean getRequestData(int slug) {

        boolean flag = false;
        String url = Api.POST_URL + slug;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            call = client.newCall(request);
            response = call.execute();
            if (response.isSuccessful()) {
                flag = true;
            }
            String responseJson = response.body().string();
            bean = gson.fromJson(responseJson, PostsContentBean.class);
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
        call.cancel();
    }
}
