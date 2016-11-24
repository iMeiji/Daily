package com.meiji.daily.mvp.zhuanlan;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.daily.R;
import com.meiji.daily.mvp.postslist.PostsListView;

import java.util.List;

import static com.meiji.daily.mvp.zhuanlan.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanBean.ZHUANLANBEAN_SLUG;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_EMOTION;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_FINANCE;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_LIFE;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_MUSIC;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_PRODUCT;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_ZHIHU;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanPresenter implements IZhuanlan.Presenter {

    private IZhuanlan.View view;
    private IZhuanlan.Model model;
    private Context mContext;
    private String[] ids;
    private List<ZhuanlanBean> list;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetAdapter();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });

    public ZhuanlanPresenter(IZhuanlan.View view, Context context) {
        this.view = view;
        this.model = new ZhuanlanModel();
        this.mContext = context;
    }

    @Override
    public void doGetType(int type) {
        view.onShowRefreshing();
        switch (type) {
            default:
            case TYPE_PRODUCT:
                ids = mContext.getResources().getStringArray(R.array.product);
                break;
            case TYPE_MUSIC:
                ids = mContext.getResources().getStringArray(R.array.music);
                break;
            case TYPE_LIFE:
                ids = mContext.getResources().getStringArray(R.array.life);
                break;
            case TYPE_EMOTION:
                ids = mContext.getResources().getStringArray(R.array.emotion);
                break;
            case TYPE_FINANCE:
                ids = mContext.getResources().getStringArray(R.array.profession);
                break;
            case TYPE_ZHIHU:
                ids = mContext.getResources().getStringArray(R.array.zhihu);
                break;
        }

        doRequestData(ids);
    }

    @Override
    public void doRequestData(final String ids[]) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.getRequestData(ids);
                System.out.println(result);
                if (result) {
                    Message message = new Message();
                    message = handler.obtainMessage();
                    message.what = 1;
                    message.sendToTarget();
                } else {
                    Message message = new Message();
                    message = handler.obtainMessage();
                    message.what = 0;
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doSetAdapter() {
        list = model.getList();
        view.onSetAdapter(list);
    }

    @Override
    public void doOnClickItem(int position) {
        String slug = list.get(position).getSlug();
        String name = list.get(position).getName();
        int postsCount = list.get(position).getPostsCount();

        Intent intent = new Intent(mContext, PostsListView.class);
        intent.putExtra(ZHUANLANBEAN_SLUG, slug);
        intent.putExtra(ZHUANLANBEAN_NAME, name);
        intent.putExtra(ZHUANLANBEAN_POSTSCOUNT, postsCount);
        Log.d(this.toString(), slug + name + postsCount);
        mContext.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        view.onRequestData();
    }
}
