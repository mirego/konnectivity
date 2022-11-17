package com.mirego.konnectivity.sample.android

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mirego.konnectivity.sample.Bootstrap
import com.mirego.konnectivity.sample.ImageResource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = findViewById(R.id.image_view)
        val textView: TextView = findViewById(R.id.text_view)

        Bootstrap.networkStateImageResource.map { it.drawableRes }
            .onEach(imageView::setImageResource)
            .launchIn(lifecycleScope)

        Bootstrap.networkStateText
            .onEach(textView::setText)
            .launchIn(lifecycleScope)
    }

    private val ImageResource.drawableRes: Int
        get() = when (this) {
            ImageResource.NETWORK_STATE_REACHABLE -> R.drawable.cloud_check
            ImageResource.NETWORK_STATE_REACHABLE_METERED -> R.drawable.cloud_alert
            ImageResource.NETWORK_STATE_UNREACHABLE -> R.drawable.cloud_off_outline
        }
}
