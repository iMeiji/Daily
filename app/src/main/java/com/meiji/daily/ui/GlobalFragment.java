package com.meiji.daily.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.meiji.daily.R;
import com.meiji.daily.adapter.ZhuanlanAdapter;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.utils.Api;
import com.meiji.daily.utils.ZhuanlanUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/11/16.
 */
public class GlobalFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final int TYPE_PRODUCT = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_LIFE = 2;
    public static final int TYPE_EMOTION = 3;
    public static final int TYPE_FINANCE = 4;
    public static final int TYPE_ZHIHU = 5;
    private int type;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private String[] ids;
    private ZhuanlanAdapter adapter;
    private ArrayList<ZhuanlanBean> list = new ArrayList<>();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                setAdapter();
            }
            return false;
        }
    });
    private Gson gson = new Gson();
    private OkHttpClient okHttpClient = new OkHttpClient();
    private ZhuanlanUtils utils = new ZhuanlanUtils();

    public GlobalFragment() {

    }

    public static GlobalFragment newInstance() {
        return new GlobalFragment();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_universal, container, false);

        initViews(view);

        switch (type) {
            default:
            case TYPE_PRODUCT:
                ids = getActivity().getResources().getStringArray(R.array.product);
                break;
            case TYPE_MUSIC:
                ids = getActivity().getResources().getStringArray(R.array.music);
                break;
            case TYPE_LIFE:
                ids = getActivity().getResources().getStringArray(R.array.life);
                break;
            case TYPE_EMOTION:
                ids = getActivity().getResources().getStringArray(R.array.emotion);
                break;
            case TYPE_FINANCE:
                ids = getActivity().getResources().getStringArray(R.array.profession);
                break;
            case TYPE_ZHIHU:
                ids = getActivity().getResources().getStringArray(R.array.zhihu);
                break;
        }
        requestData();

        return view;
    }

    private void requestData() {
        if (list.size() != 0) {
            list.clear();
        }

        refreshLayout.setRefreshing(true);

        for (int i = 0; i < ids.length; i++) {
            final Request request = new Request.Builder()
                    .url(Api.BASE_URL + ids[i])
                    .get()
                    .build();

            final int finalI = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Response response = okHttpClient.newCall(request).execute();
//                        if (response.isSuccessful()) {
//                            String responseJson = response.body().string();
//                            //System.out.println(responseJson);
////                            ZhuanlanBean bean = gson.fromJson(responseJson, ZhuanlanBean.class);
////                            list.add(bean);
//                            if (finalI == ids.length - 1) {
//                                /*Message message = handler.obtainMessage();
//                                message.what = 1;
//                                message.sendToTarget();*/
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String responseJson = response.body().string();
                    ZhuanlanBean bean = gson.fromJson(responseJson, ZhuanlanBean.class);
                    list.add(bean);
                    System.out.println(finalI + "---" + responseJson);
                    if (finalI == ids.length - 1) {
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.sendToTarget();
                    }
                }
            });
        }

    }

    private void setAdapter() {
        recyclerView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new ZhuanlanAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            refreshLayout.setRefreshing(false);
        } else {
            recyclerView.setAdapter(adapter);
// 有毒           adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }

    private void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        recyclerView.setVisibility(View.GONE);
        requestData();
    }
}
