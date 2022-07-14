package com.ma.lightweather.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import com.google.android.material.tabs.TabLayoutMediator
import com.ma.lightweather.adapter.ViewPager2Adapter
import com.ma.lightweather.adapter.ViewPagerAdapter
import com.ma.lightweather.app.Contants
import com.ma.lightweather.databinding.ActivityFrogBinding
import com.ma.lightweather.utils.CommonUtils
import com.ma.lightweather.utils.SPUtils
import com.ma.lightweather.widget.SearchView
import com.ma.lightweather.widget.WeatherSearchView

class FrogActivity : BaseActivity<ActivityFrogBinding>() {

    private lateinit var viewPager2Adapter: ViewPager2Adapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityFrogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        viewPagerAdapter= ViewPagerAdapter(supportFragmentManager)
        mBinding.viewPager.adapter=viewPagerAdapter
//        viewPager2Adapter= ViewPager2Adapter(this)
//        mBinding.viewPager.adapter=viewPager2Adapter
//        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
//            when(position){
//                0->{
//                    tab.text="今天"
//                }
//                1->{
//                    tab.text="未来"
//                }
//            }
//        }.attach()
        supportFragmentManager.setFragmentResultListener("backgroundColor", this) { requestKey, bundle ->
            val backgroundColor = bundle.getInt("backgroundColor")
            mBinding.tabLayout.setBackgroundColor(backgroundColor)
            mBinding.searchView.setBackColor(backgroundColor)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor=backgroundColor
            }else{

            }
        }


        mBinding.searchView.setState(false)
        mBinding.searchView.setCursorState(false)
        mBinding.searchView.setOnLeftClickListener {
            if (it == SearchView.LEFT_IV_SEARCH) {
                toSearch()
            }
        }

        mBinding.searchView.setOnAccountClickListener{
            if(it==SearchView.RIGHT_ACCOUNT_ACCOUNT){
                mBinding.drawerLayout.openDrawer(Gravity.LEFT)
            }
        }
        mBinding.searchView.setOnFocusChangeListener {
            if (it){
                toSearch()
            }
            false
        }
    }

    private fun toSearch() {
        mBinding.searchView.clearEtFocus()
        var it= Intent(this@FrogActivity, SearchActivity::class.java)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            startActivity(it,ActivityOptions.makeSceneTransitionAnimation(
                    this@FrogActivity,mBinding.searchView,"searchView"
            ).toBundle())
        }else{
            startActivity(it)
        }
    }
}