package com.ma.lightweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ma.lightweather.R
import com.ma.lightweather.model.HFWeather
import com.ma.lightweather.utils.CommonUtils
import com.ma.lightweather.utils.WeatherUtils

class FutureDaysAdapter(private val context: Context, private val futureList: List<HFWeather.HFWeatherFuture>) :BaseExpandableListAdapter(){
    override fun getGroup(p0: Int): Any {
        return futureList[p0]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View? {
        var view=p2
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_future_group,p3,false)
        }
        val futerweDateTv: TextView? =view?.findViewById(R.id.future_date)
        val futerweWeatherTv: TextView? =view?.findViewById(R.id.future_weather)
        val futerweHighTv: TextView? =view?.findViewById(R.id.future_high)
        val futerweLowTv: TextView? =view?.findViewById(R.id.future_low)
        val futerweImgIv: ImageView? =view?.findViewById(R.id.future_icon)
        val rlLayout: RelativeLayout? =view?.findViewById(R.id.rlLayout)
        futerweDateTv?.text==futureList[p0].fxDate
        futerweWeatherTv?.text=futureList[p0].textDay
        futerweHighTv?.text=futureList[p0].tempMax+"℃"
        futerweLowTv?.text=futureList[p0].tempMin+"℃"
        futerweImgIv?.setImageResource(WeatherUtils.getFrogWeatherIcon(futureList[p0].textDay))
        rlLayout?.setBackgroundColor(ContextCompat.getColor(context!!,WeatherUtils.getFrogWeatherBack(futureList[p0].textDay)))
        return view
    }

    override fun getChildrenCount(p0: Int): Int {
        return 1
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return futureList[p0]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View? {
        var view=p3
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_future_child,p4,false)
        }
        val futerweWindTv: TextView? =view?.findViewById(R.id.future_wind)
        val futerweHumTv: TextView? =view?.findViewById(R.id.future_hum)
        val futerweVisTv: TextView? =view?.findViewById(R.id.future_vis)
        val futerwePopTv: TextView? =view?.findViewById(R.id.future_pop)
        val futerweUvTv: TextView? =view?.findViewById(R.id.future_uv)
        val futerweSunTv: TextView? =view?.findViewById(R.id.future_sun)

        futerweWindTv?.text=futureList[p0].windScaleDay+"级，"+futureList[p0].windSpeedDay+"公里/小时，"+futureList[p0].windDirDay
        futerweHumTv?.text=futureList[p0].humidity+"%"
        futerweVisTv?.text=futureList[p0].vis+"公里"
        futerwePopTv?.text=futureList[p0].precip+"%"
        when(val uv=futureList[p0].uvIndex.toInt()){
            in 0..2->{
                futerweUvTv?.text="一级较弱，"+uv
            }
            in 3..4->{
                futerweUvTv?.text="二级较弱，"+uv
            }
            in 5..6->{
                futerweUvTv?.text="三级较强，"+uv
            }
            in 7..9->{
                futerweUvTv?.text="四级很强，"+uv
            }
            in 10..20 ->{
                futerweUvTv?.text="五级特强，"+uv
            }
        }
        val sunrise=CommonUtils.getTimeFormat(futureList[p0].sunrise)
        val sunset=CommonUtils.getTimeFormat(futureList[p0].sunset)
        futerweSunTv?.text=CommonUtils.change24To12(sunrise[0])+":"+sunrise[1]+","+
                CommonUtils.change24To12(sunset[0])+":"+sunset[1]
        return view
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getGroupCount(): Int {
        return futureList.size
    }
}