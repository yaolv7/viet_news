package com.viet.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.viet.mine.R
import com.viet.news.core.ext.clickWithTrigger
import com.viet.news.core.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine_setting_change_phone.*

/**
 * @Description 修改手机号
 * @Author null
 * @Email zongjia.long@merculet.io
 * @Version
 */
class ChangePhoneNumberFragment : BaseFragment() {
    private var mContainerView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContainerView = inflater.inflate(R.layout.fragment_mine_setting_change_phone, container, false)
        return mContainerView
    }

    override fun initView(view: View) {
        confirm_btn.clickWithTrigger {
            Navigation.findNavController(confirm_btn).navigate(R.id.action_page_2_1)
        }
    }
}