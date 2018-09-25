package com.viet.news.core.http.interceptor

import android.annotation.SuppressLint
import com.viet.news.core.R
import com.viet.news.core.api.HttpResponse
import com.viet.news.core.api.RetrofitManager
import com.viet.news.core.config.Config
import com.viet.news.core.config.IActivityManager
import com.viet.news.core.config.LoginEnum
import com.viet.news.core.domain.HandleException
import com.viet.news.core.domain.User
import com.viet.news.core.domain.request.LoginParams
import com.viet.news.core.domain.response.LoginRegisterResponse
import com.viet.news.core.ext.routerWithAnim
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * @Description 登录权限
 * @Author sean
 * @Email xiao.lu@magicwindow.cn
 * @Date 07/05/2018 11:22 AM
 * @Version
 */
class HttpLoginInterceptor : Interceptor {

    var isLoginInvalidate: Boolean = false

    @SuppressLint("CheckResult")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val response = chain.proceed(request)
        return when {
            response.code() == HandleException.FORBIDDEN -> {
                if (!isLoginInvalidate) {
                    isLoginInvalidate = true
                    routerWithAnim(Config.ROUTER_LOGIN_ACTIVITY).anim(R.anim.activity_open,android.R.anim.fade_out).go(IActivityManager.lastActivity())
                }
                isLoginInvalidate = false
                response
            }
            response.code() == HandleException.UNAUTHORIZED -> {
                val param = LoginParams()
                param.setType(LoginEnum.HARDWARE)
                //由于GsonFactory强制加了一层HttpResponse，所以此处强制转换
                val data = RetrofitManager.get().apiService()
                        .logins(param)
                        .execute()
                        .body() as HttpResponse<LoginRegisterResponse>?
                val token = data?.data?.token.toString()
                User.currentUser.accessToken = token
                response?.body()?.close()
                chain.proceed(request.newBuilder().header("Authorization", token).build())  //proceed继续执行该请求
            }
            else -> {
                isLoginInvalidate = false
                response.newBuilder()
                        .body(ResponseBody.create(response.body()?.contentType(), response.body()?.string()))
                        .build()
            }
        }
    }

}