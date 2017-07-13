package com.meiji.daily;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.mvp.base.BaseActivity;
import com.meiji.daily.mvp.base.IBasePresenter;
import com.meiji.daily.util.SettingUtil;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

import static com.meiji.daily.R.id.changelogView;

/**
 * Created by Meiji on 2016/12/3.
 */

public class AboutActivity extends BaseActivity<IBasePresenter> implements View.OnClickListener {

    private TextView tv_version;

    @Override
    protected int attachLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tv_version.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        LinearLayout changelogView = (LinearLayout) findViewById(R.id.changelogView);
        LinearLayout developersView = (LinearLayout) findViewById(R.id.developersView);
        LinearLayout licensesView = (LinearLayout) findViewById(R.id.licensesView);
        LinearLayout sourceCodeView = (LinearLayout) findViewById(R.id.sourceCodeView);
        LinearLayout copyRightView = (LinearLayout) findViewById(R.id.copyRightView);
        initToolBar((Toolbar) findViewById(R.id.toolbar), true, null);

        changelogView.setOnClickListener(this);
        developersView.setOnClickListener(this);
        licensesView.setOnClickListener(this);
        sourceCodeView.setOnClickListener(this);
        copyRightView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case changelogView:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))));
                break;
            case R.id.developersView:
                new MaterialDialog.Builder(this)
                        .title(R.string.about_developers_label)
                        .content(R.string.about_developers)
                        .positiveText(android.R.string.ok)
                        .show();
                break;
            case R.id.licensesView:
                createLicenseDialog();
                break;
            case R.id.sourceCodeView:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))));
                break;
            case R.id.copyRightView:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.action_copyright)
                        .setMessage(R.string.copyright_content)
                        .setPositiveButton(R.string.md_got_it, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(SettingUtil.getInstance().getColor());
                dialog.show();
//                new MaterialDialog.Builder(this)
//                        .title(R.string.action_copyright)
//                        .content(R.string.copyright_content)
//                        .positiveText(R.string.md_got_it)
//                        .build()
//                        .show();
                break;
        }
    }

    private void createLicenseDialog() {
        Notices notices = new Notices();
        notices.addNotice(new Notice("Material Dialogs", "https://github.com/afollestad/material-dialogs", "Copyright (c) 2014-2016 Aidan Michael Follestad", new MITLicense()));
        notices.addNotice(new Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Gson", "https://github.com/google/gson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("CircleImageView", "https://github.com/hdodenhof/CircleImageView", "Copyright 2014 - 2016 Henning Dodenhof", new ApacheSoftwareLicense20()));

        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text) + getString(R.string.source_code_url));
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}