package com.meiji.daily;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.utils.Api;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_USERADD;

/**
 * Created by Meiji on 2016/12/1.
 */

public class AddActivity extends BaseActivity {

    private ZhuanlanDao zhuanlanDao = new ZhuanlanDao();
    private boolean result = false;
    private MaterialDialog materialDialog;

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
            final String slug = matcher.group(1).toLowerCase();
            List<ZhuanlanBean> query = zhuanlanDao.query(TYPE_USERADD);
            for (ZhuanlanBean bean : query) {
                if (bean.getSlug().equals(slug)) {
                    onFinish(getString(R.string.has_been_added));
                    return;
                }
            }

            Api api = RetrofitFactory.getRetrofit().create(Api.class);
            Call<ZhuanlanBean> call = api.getZhuanlanBean(slug);
            call.enqueue(new Callback<ZhuanlanBean>() {
                @Override
                public void onResponse(Call<ZhuanlanBean> call, Response<ZhuanlanBean> response) {
                    try {
                        ZhuanlanBean bean = response.body();
                        String type = String.valueOf(TYPE_USERADD);
                        String avatarUrl = bean.getAvatar().getTemplate();
                        String avatarId = bean.getAvatar().getId();
                        String name = bean.getName();
                        String followersCount = String.valueOf(bean.getFollowersCount());
                        String postsCount = String.valueOf(bean.getPostsCount());
                        String intro = bean.getIntro();
                        String slug = bean.getSlug();
                        result = zhuanlanDao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    if (result) {
                        onFinish("保存成功");
                    } else {
                        onFinish("保存失败");
                    }
                }

                @Override
                public void onFailure(Call<ZhuanlanBean> call, Throwable t) {
                    onFinish("保存失败");
                }
            });

        } else {
            onFinish(getString(R.string.incorrect_link));
        }
    }

    private void onFinish(String message) {
        materialDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 800);
    }
}
