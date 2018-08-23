package com.viet.news.core.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.viet.news.core.api.ApiResponse
import com.viet.news.core.vo.Resource

/**
 * @Author Aaron
 * @Email aaron@magicwindow.cn
 * @Description  如果不需要DB存储，则用此类。可以省略部分相关代码
 */
abstract class NetworkOnlyResource<RequestType> @MainThread constructor() : NetworkBoundResource<RequestType, RequestType>() {
    private val result = MediatorLiveData<Resource<RequestType>>()

    // 将网络获取的数据存储到db
    @WorkerThread
    override fun saveCallResult(item: RequestType) {
    }


    // 是否需要从网络重新获取
    @MainThread
    override fun shouldFetch(data: RequestType?): Boolean {
        return true
    }

    // 从db内获取cache数据
    @MainThread
    override fun loadFromDb(): LiveData<RequestType> {
        return MutableLiveData<RequestType>()
    }

    // 从网络中获取
    @MainThread
    abstract override fun createCall(): LiveData<ApiResponse<RequestType>>

    // 获取失败
    @MainThread
    override fun onFetchFailed() {
    }

}