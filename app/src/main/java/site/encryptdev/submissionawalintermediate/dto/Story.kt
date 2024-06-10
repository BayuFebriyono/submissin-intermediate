package site.encryptdev.submissionawalintermediate.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val name: String,
    val description: String,
    val imgUrl: String
) : Parcelable