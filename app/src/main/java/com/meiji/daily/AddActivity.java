package com.meiji.daily;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.utils.Api;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_USERADD;

/**
 * Created by Meiji on 2016/12/1.
 */

public class AddActivity extends BaseActivity {

    private Gson gson = new Gson();
    private ZhuanlanDao zhuanlanDao = new ZhuanlanDao();
    private boolean result = false;
    private MaterialDialog materialDialog;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            materialDialog.dismiss();
            if (message.what == 1) {
                onFinish("保存成功");
            }
            if (message.what == 0) {
                onFinish("保存失败");
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.md_loading)
                .cancelable(true)
                .build();
        materialDialog.show();

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (action.equals(Intent.ACTION_SEND) && type != null) {
            if (type.equals("text/plain")) {
                String shareText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (!TextUtils.isEmpty(shareText)) {
                    handleSendText(shareText);
                }
            }
        } else {
            onFinish(getString(R.string.formal_incorrect));
        }
    }

    private void handleSendText(String shareText) {
        final String regex = "^.*http.*://zhuanlan.zhihu.com/(.*)$";
        final Matcher matcher = Pattern.compile(regex).matcher(shareText);
        if (matcher.find()) {
            String slug = matcher.group(1);
            List<ZhuanlanBean> query = zhuanlanDao.query(TYPE_USERADD);
            for (ZhuanlanBean bean : query) {
                if (bean.getSlug().equals(slug)) {
                    onFinish(getString(R.string.has_been_added));
                    return;
                }
            }
            final String url = Api.BASE_URL + slug;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new Request.Builder().url(url).get().build();
                    try {
                        Response response = new OkHttpClient().newCall(request).execute();
                        String responseJson = response.body().string();
                        ZhuanlanBean bean = gson.fromJson(responseJson, ZhuanlanBean.class);
                        String type = String.valueOf(TYPE_USERADD);
                        String avatarUrl = bean.getAvatar().getTemplate();
                        String avatarId = bean.getAvatar().getId();
                        String name = bean.getName();
                        String followersCount = String.valueOf(bean.getFollowersCount());
                        String postsCount = String.valueOf(bean.getPostsCount());
                        String intro = bean.getIntro();
                        String slug = bean.getSlug();
                        result = zhuanlanDao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
                        if (result) {
                            Message message = handler.obtainMessage(1);
                            message.sendToTarget();
                        } else {
                            Message message = handler.obtainMessage(0);
                            message.sendToTarget();
                        }
                    } catch (IOException | JsonSyntaxException e) {
                        e.printStackTrace();
                        Message message = handler.obtainMessage(0);
                        message.sendToTarget();
                    }
                }
            }).start();
        } else {
            onFinish(getString(R.string.incorrect_link));
        }
    }

    private void onFinish(String message) {
        materialDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 800);
    }
}
