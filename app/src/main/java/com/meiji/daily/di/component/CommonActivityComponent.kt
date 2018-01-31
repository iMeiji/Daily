package com.meiji.daily.di.component

import com.meiji.daily.MainActivity
import com.meiji.daily.di.scope.ActivityScoped
import com.meiji.daily.module.base.BaseActivity

import dagger.Component

/**
 * Created by Meiji on 2017/12/28.
 */

@ActivityScoped
@Component(dependencies = arrayOf(AppComponent::class))
interface CommonActivityComponent {

    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)
}
