package com.johndeweydev.notesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int,
    val title: String,
    val description: String?,
    val dateInfo: NoteDateInfo
): Parcelable
