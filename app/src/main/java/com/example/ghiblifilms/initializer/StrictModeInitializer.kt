package com.example.ghiblifilms.initializer

import android.content.Context
import android.os.StrictMode
import androidx.startup.Initializer
import com.example.ghiblifilms.BuildConfig

class StrictModeInitializer : Initializer<Unit> {

    override fun create(context: Context) {

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .detectResourceMismatches()
                    .penaltyLog()
                    .build()
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

