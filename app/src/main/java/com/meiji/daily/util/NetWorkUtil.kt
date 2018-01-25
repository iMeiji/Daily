package com.meiji.daily.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Meiji on 2017/5/2.
 */

object NetWorkUtil {

    /**
     * 判断是否有网络连接
     */
    fun isNetworkConnected(context: Context): Boolean {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取NetworkInfo对象
        val networkInfo = manager.activeNetworkInfo
        //判断NetworkInfo对象是否为空
        return networkInfo?.isAvailable ?: false
    }

    /**
     * 判断WIFI网络是否可用
     */
    fun isWifiConnected(context: Context): Boolean {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取NetworkInfo对象
        val networkInfo = manager.activeNetworkInfo
        //判断NetworkInfo对象是否为空 并且类型是否为WIFI
        return networkInfo?.isAvailable ?: false && networkInfo?.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 判断MOBILE网络是否可用
     */
    fun isMobileConnected(context: Context): Boolean {
        //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //获取NetworkInfo对象
        val networkInfo = manager.activeNetworkInfo
        //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
        return networkInfo?.isAvailable ?: false && networkInfo?.type == ConnectivityManager.TYPE_MOBILE
    }
}
