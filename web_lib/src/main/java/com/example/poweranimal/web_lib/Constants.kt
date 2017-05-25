package com.example.poweranimal.web_lib

import java.util.*

/**
 * Created by felixproehl on 23.05.17.
 */
object Constants {

    val USER_AGENT = "Mozilla/5.0 (Linux; U; Android "
            .plus(android.os.Build.VERSION.RELEASE + ";")
            .plus(Locale.getDefault().toString() + "; ")
            .plus(android.os.Build.DEVICE+ "/" + android.os.Build.ID + ")")
}