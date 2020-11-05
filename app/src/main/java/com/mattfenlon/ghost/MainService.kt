package com.mattfenlon.ghost


import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.*
import android.view.WindowManager.LayoutParams
import android.widget.FrameLayout

/**
 * Created by noln on 22/09/2019.
 */
class MainService : Service() {

    private lateinit var windowManager: WindowManager
    private var floatyView: View? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        addOverlayView()
    }

    private fun addOverlayView() {

        val params: LayoutParams
        val layoutParamsType: Int = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            LayoutParams.TYPE_PHONE
        }

        params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                layoutParamsType,
                LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT,
                PixelFormat.TRANSLUCENT)

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        with(inflater.inflate(R.layout.floating_view, null)){
            windowManager.addView(this, params)
        }
    }

    fun onClickCancel(v: View){
        stopSelf()
    }

    fun onClickCommand(v: View){

    }

    override fun onDestroy() {
        super.onDestroy()

        floatyView?.let {
            windowManager.removeView(it)
            floatyView = null
        }
    }

    companion object {
        private val TAG = MainService::class.java.simpleName
    }
}
