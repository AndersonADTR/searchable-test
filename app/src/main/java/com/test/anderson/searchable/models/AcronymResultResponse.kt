package com.test.anderson.searchable.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LfsItem(

	@field:SerializedName("freq")
	val freq: Int? = null,

	@field:SerializedName("lf")
	val lf: String? = null,

	@field:SerializedName("vars")
	val vars: List<VarsItem>? = null,

	@field:SerializedName("since")
	val since: Int? = null
): Parcelable

@Parcelize
data class VarsItem(

	@field:SerializedName("freq")
	val freq: Int? = null,

	@field:SerializedName("lf")
	val lf: String? = null,

	@field:SerializedName("since")
	val since: Int? = null
): Parcelable

@Parcelize
data class AcronymResultResponseItem(

	@field:SerializedName("sf")
	val sf: String? = null,

	@field:SerializedName("lfs")
	val lfs: List<LfsItem>? = null
): Parcelable
