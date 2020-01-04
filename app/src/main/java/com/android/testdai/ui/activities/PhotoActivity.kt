package com.android.testdai.ui.activities

import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.android.testdai.R
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity: BaseActivity() {

    companion object{
        const val PHOTO_URL = "photo_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        intent?.extras?.getString(PHOTO_URL)?.let {
            GlideApp.with(this)
                    .load(it)
                    .placeholder(R.drawable.empty)
                    .error(R.drawable.empty)
                    .into(photo as AppCompatImageView)
        }
    }


    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this)
    }
}