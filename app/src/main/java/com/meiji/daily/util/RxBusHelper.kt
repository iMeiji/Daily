package com.meiji.daily.util

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Meiji on 2017/12/29.
 * 参考 ：https://juejin.im/entry/58ff2e26a0bb9f0065d2c5f2
 */

@Singleton
class RxBusHelper @Inject constructor() {

    private val mPublishMap = ConcurrentHashMap<Any, MutableList<PublishProcessor<Any>>>()

    fun <T> register(clz: Class<T>): Flowable<Any> {
        return register(clz.name)
    }

    fun register(tag: Any): Flowable<Any> {
        var processorList = mPublishMap[tag]
        if (null == processorList) {
            processorList = mutableListOf()
            mPublishMap[tag] = processorList
        }

        val processor = PublishProcessor.create<Any>()
        processorList.add(processor)

        //System.out.println("注册到rxbus");
        return processor
    }

    fun <T> unregister(clz: Class<T>, flowable: Flowable<*>) {
        unregister(clz.name, flowable)
    }

    fun unregister(tag: Any, flowable: Flowable<*>) {
        val processorList = mPublishMap[tag]
        if (null != processorList) {
            processorList.remove(flowable)
            if (processorList.isEmpty()) {
                mPublishMap.remove(tag)
                //System.out.println("从rxbus取消注册");
            }
        }
    }

    fun post(content: Any) {
        post(content.javaClass.name, content)
    }

    fun post(tag: Any, content: Any) {
        val processorList = mPublishMap[tag]
        if (!processorList?.isEmpty()!!) {
            for (processor in processorList) {
                processor.onNext(content)
            }
        }
    }
}
