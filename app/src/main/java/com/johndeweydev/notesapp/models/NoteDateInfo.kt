package com.johndeweydev.notesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteDateInfo(
    val created_at: Int?,
    val updated_at: Int?,
    var deleted_at: Int?
): Parcelable