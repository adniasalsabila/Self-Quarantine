package org.d3if4133.side_project_adnia.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Corona(
    val positif: String,
    val sembuh: String,
    val meninggal: String
) : Parcelable