package com.meiji.daily.mvp.useradd;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.R;
import com.meiji.daily.adapter.ZhuanlanAdapter;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.interfaces.IOnItemClickListener;

import java.util.List;

/**
 * Created by Meiji on 2016/11/27.
 */

public class UseraddView extends Fragment implements IUseradd.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private TextView tv_description;
    private RecyclerView recycler_view;
    private FloatingActionButton fab_add;
    private SwipeRefreshLayout refresh_layout;
    private MaterialDialog dialog;

    private IUseradd.Presenter presenter;
    private ZhuanlanAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_useradd, container, false);
        presenter = new UseraddPresenter(this, getActivity());
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        if (presenter.doQueryDB()) {
            tv_description.setVisibility(View.GONE);
        } else {
            tv_description.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 设置手指在屏幕上下拉多少距离开始刷新
        refresh_layout.setDistanceToTriggerSync(300);
        // 设置下拉刷新按钮的背景颜色
        refresh_layout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置下拉刷新按钮的大小
        refresh_layout.setSize(SwipeRefreshLayout.DEFAULT);
        refresh_layout.setOnRefreshListener(this);

        fab_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                createDialog();
                break;
        }
    }

    private void createDialog() {
        dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.md_zhuanlan_add_title)
                .content(R.string.md_zhuanlan_add_content)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                })
                .build();

        // 设置3个按键
        dialog.setActionButton(DialogAction.NEGATIVE, R.string.cancel);
        dialog.setActionButton(DialogAction.POSITIVE, R.string.ok);
        dialog.setActionButton(DialogAction.NEUTRAL, R.string.md_zhuanlan_add_help);

        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 校验填写 id 是否正确
                onCheckInputId();
                dialog.dismiss();
            }
        });

        dialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 什么是专栏 id
            }
        });

        dialog.show();
    }

    private void onCheckInputId() {
        String input = dialog.getInputEditText().getText().toString();
        if (!TextUtils.isEmpty(input)) {
            presenter.doCheckInputId(input);
        }
    }

    @Override
    public void onSetAdapter(List<ZhuanlanBean> list) {
        adapter = new ZhuanlanAdapter(getActivity(), list);
        recycler_view.setAdapter(adapter);
        adapter.setItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(android.view.View view, int position) {
                presenter.doOnClickItem(position);
            }
        });
    }

    @Override
    public void onShowRefreshing() {
        refresh_layout.setRefreshing(true);
        recycler_view.setVisibility(android.view.View.GONE);
    }

    @Override
    public void onHideRefreshing() {
        refresh_layout.setRefreshing(false);
        recycler_view.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onFail() {
        Snackbar.make(refresh_layout, R.string.add_zhuanlan_id_error, Snackbar.LENGTH_SHORT).show();
        refresh_layout.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        Snackbar.make(refresh_layout, R.string.add_zhuanlan_id_success, Snackbar.LENGTH_SHORT).show();
    }
}
