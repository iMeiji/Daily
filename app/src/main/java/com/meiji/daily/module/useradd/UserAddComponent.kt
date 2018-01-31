package com.meiji.daily.module.useradd

import com.meiji.daily.di.component.AppComponent
import com.meiji.daily.di.scope.FragmentScoped

import dagger.Component

/**
 * Created by Meiji on 2017/12/28.
 */
@FragmentScoped
@Component(modules = arrayOf(UserAddModule::class), dependencies = arrayOf(AppComponent::class))
interface UserAddComponent {

    fun inject(userAddView: UserAddView)
}
