package com.example.ugd89_b_10691_project1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.annotation.SuppressLint
import android.hardware.Camera
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    lateinit var proximitySensor: Sensor
    lateinit var sensorManager: SensorManager

    var proximitySensorEventListener: SensorEventListener? = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { }

        override fun onSensorChanged(event: SensorEvent) {
            if(event.sensor.type == Sensor.TYPE_PROXIMITY){
                if(event.values[0] == 0f){
                    mCamera?.stopPreview()
                    mCamera?.release()
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
                    mCameraView = CameraView(this@MainActivity, mCamera!!)
                    val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
                    camera_view.addView(mCameraView)
                }else{
                    mCamera?.stopPreview()
                    mCamera?.release()
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK)
                    mCameraView = CameraView(this@MainActivity, mCamera!!)
                    val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
                    camera_view.addView(mCameraView)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            mCamera = Camera.open()
        } catch (e: Exception) {
            Log.d("Error", "Failed to get Camera" + e.message)
        }
        if(mCamera != null) {
            mCameraView = CameraView(this, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }
        @SuppressLint("MissingInflatedId", "LocalSuppress") val imageClose =
            findViewById<View>(R.id.imgClose) as ImageButton
        imageClose.setOnClickListener{ view: View? -> System.exit(0)}
    }
}