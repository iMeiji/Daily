package com.meiji.daily.zhuanlan.presenter;

import android.os.Handler;
import android.os.Message;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.zhuanlan.model.IZhuanlanMode;
import com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl;
import com.meiji.daily.zhuanlan.view.IZhuanlanView;

import java.util.List;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanPresenterImpl implements IZhuanlanPresenter {

    private IZhuanlanView view;
    private IZhuanlanMode mode;
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

    public ZhuanlanPresenterImpl(IZhuanlanView view) {
        this.view = view;
        this.mode = new ZhuanlanModeImpl();
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
        List<ZhuanlanBean> list = mode.getList();
        view.onSetAdapter(list);
    }
}
