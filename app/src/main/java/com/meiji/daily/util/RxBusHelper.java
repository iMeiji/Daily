package com.meiji.daily.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Meiji on 2017/12/29.
 * 参考 ：https://juejin.im/entry/58ff2e26a0bb9f0065d2c5f2
 */

@Singleton
public class RxBusHelper {

    private ConcurrentHashMap<Object, List<PublishProcessor>> mPublishMap = new ConcurrentHashMap<>();

    @Inject
    public RxBusHelper() {

    }

    public <T> Flowable<T> register(@NonNull Class<T> clz) {
        return register(clz.getName());
    }

    public <T> Flowable<T> register(@NonNull Object tag) {
        List<PublishProcessor> processorList = mPublishMap.get(tag);
        if (null == processorList) {
            processorList = new ArrayList<>();
            mPublishMap.put(tag, processorList);
        }

        PublishProcessor<T> processor = PublishProcessor.create();
        processorList.add(processor);

        //System.out.println("注册到rxbus");
        return processor;
    }

    public <T> void unregister(@NonNull Class<T> clz, @NonNull Flowable flowable) {
        unregister(clz.getName(), flowable);
    }

    public void unregister(@NonNull Object tag, @NonNull Flowable flowable) {
        List<PublishProcessor> processorList = mPublishMap.get(tag);
        if (null != processorList) {
            processorList.remove(flowable);
            if (processorList.isEmpty()) {
                mPublishMap.remove(tag);
                //System.out.println("从rxbus取消注册");
            }
        }
    }

    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    public void post(@NonNull Object tag, @NonNull Object content) {
        List<PublishProcessor> processorList = mPublishMap.get(tag);
        if (!processorList.isEmpty()) {
            for (PublishProcessor processor : processorList) {
                processor.onNext(content);
            }
        }
    }
}
