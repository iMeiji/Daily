package com.meiji.daily.mvp.useradd;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.binder.ZhuanlanViewBinder;
import com.meiji.daily.mvp.base.BaseFragment;
import com.meiji.daily.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2016/11/27.
 */

public class UseraddView extends BaseFragment<IUseradd.Presenter> implements IUseradd.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView tv_description;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private MaterialDialog dialog;

    private MultiTypeAdapter adapter;
    private List<ZhuanlanBean> list = new ArrayList<>();

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_useradd;
    }

    @Override
    protected void initData() {
        presenter.doSetAdapter();
    }

    @Override
    protected void initViews(View view) {
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        FloatingActionButton fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeColors(ColorUtils.getColor());
        refresh_layout.setOnRefreshListener(this);

        adapter = new MultiTypeAdapter();
        adapter.register(ZhuanlanBean.class, new ZhuanlanViewBinder());
        recycler_view.setAdapter(adapter);

        fab_add.setOnClickListener(this);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        final ZhuanlanBean bean = list.get(position);
                        final String name = list.get(position).getName();
                        adapter.notifyItemRemoved(position);
                        presenter.doRemoveItem(position);
                        Snackbar.make(recycler_view, getString(R.string.deleted) + name, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        presenter.doRemoveItemCancel(bean);
                                    }
                                })
                                .show();
                    }
                });
        helper.attachToRecyclerView(recycler_view);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_add) {
            createDialog();
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
        dialog.setActionButton(DialogAction.NEGATIVE, R.string.md_cancel);
        dialog.setActionButton(DialogAction.POSITIVE, R.string.md_ok);
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.add_zhuanlan_id_help_url))));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onCheckInputId() {
        String input = dialog.getInputEditText().getText().toString();
        if (!TextUtils.isEmpty(input)) {
            presenter.doCheckInputId(input.trim().toLowerCase());
        }
    }

    @Override
    public void onSetAdapter(final List<ZhuanlanBean> mlist) {
        list = mlist;
        adapter.setItems(list);
        adapter.notifyDataSetChanged();

        if (list.size() == 0) {
            tv_description.setVisibility(View.VISIBLE);
        } else {
            tv_description.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onAddSuccess() {
        Snackbar.make(recycler_view, R.string.add_zhuanlan_id_success, Snackbar.LENGTH_SHORT).show();
        tv_description.setVisibility(View.GONE);
    }

    @Override
    public void onShowLoading() {
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(recycler_view, R.string.add_zhuanlan_id_error, Snackbar.LENGTH_SHORT).show();
        refresh_layout.setEnabled(true);
    }

    @Override
    public void setPresenter(IUseradd.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new UseraddPresenter(this);
        }
    }
}
