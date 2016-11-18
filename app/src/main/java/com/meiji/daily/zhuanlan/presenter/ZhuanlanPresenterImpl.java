package com.meiji.daily.zhuanlan.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.zhuanlan.model.IZhuanlanMode;
import com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl;
import com.meiji.daily.zhuanlan.view.IZhuanlanView;

import java.util.List;

import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_EMOTION;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_FINANCE;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_LIFE;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_MUSIC;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_PRODUCT;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_ZHIHU;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanPresenterImpl implements IZhuanlanPresenter {

    private IZhuanlanView view;
    private IZhuanlanMode mode;
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
                view.onFail();
            }
            return false;
        }
    });

    public ZhuanlanPresenterImpl(IZhuanlanView view, Context context) {
        this.view = view;
        this.mode = new ZhuanlanModeImpl();
        this.mContext = context;
    }

    @Override
    public void doGetType(int type) {
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
                boolean result = mode.getRequestData(ids);
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
        list = mode.getList();
        view.onSetAdapter(list);
    }

    @Override
    public void doOnClickItem(int position) {
        String slug = list.get(position).getSlug();
        String name = list.get(position).getName();
        int postsCount = list.get(position).getPostsCount();
        Log.d(this.toString(), slug + name + postsCount);
    }
}
