package com.meiji.daily.mvp.zhuanlan;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.dao.ZhuanlanDao;
import com.meiji.daily.mvp.postslist.PostsListView;

import java.util.List;

import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_SLUG;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_EMOTION;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_FINANCE;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_LIFE;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_MUSIC;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_PRODUCT;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_ZHIHU;

/**
 * Created by Meiji on 2016/11/17.
 */

class ZhuanlanPresenter implements IZhuanlan.Presenter {

    private IZhuanlan.View view;
    private IZhuanlan.Model model;
    private Context mContext;
    private String[] ids;
    private List<ZhuanlanBean> list;
    private int type;
    private ZhuanlanDao dao;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSaveData();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });

    ZhuanlanPresenter(IZhuanlan.View view, Context context) {
        this.view = view;
        this.model = new ZhuanlanModel();
        this.mContext = context;
        dao = new ZhuanlanDao(mContext);
    }

    @Override
    public void doGetType(int type) {
        this.type = type;
        view.onShowRefreshing();
        switch (this.type) {
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
        doRequestData();
    }

    @Override
    public void doRequestData() {
        list = dao.query(type);
        if (list.size() != ids.length) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean result = model.getRequestData(ids);
                    if (result) {
                        Message message = handler.obtainMessage(1);
                        message.sendToTarget();
                    } else {
                        Message message = handler.obtainMessage(0);
                        message.sendToTarget();
                    }
                }
            }).start();
        } else {
            doSetAdapter();
        }
    }

    @Override
    public void doSaveData() {
        list = model.getList();
        for (ZhuanlanBean bean : list) {
            String type = String.valueOf(this.type);
            String avatarUrl = bean.getAvatar().getTemplate();
            String avatarId = bean.getAvatar().getId();
            String name = bean.getName();
            String followersCount = String.valueOf(bean.getFollowersCount());
            String postsCount = String.valueOf(bean.getPostsCount());
            String intro = bean.getIntro();
            String slug = bean.getSlug();
            dao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
        }
        doSetAdapter();
    }

    @Override
    public void doSetAdapter() {
        list = dao.query(type);
        view.onSetAdapter(list);
        view.onHideRefreshing();
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
        mContext.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        view.onHideRefreshing();
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
