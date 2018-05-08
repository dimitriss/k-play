package com.mbiamont.kplay.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Album(val id: Long = 0,
                 val title: String = "",
                 val cover: String = "",
                 @SerializedName("cover_small") val coverSmall: String = "",
                 @SerializedName("cover_medium") val coverMedium: String = "",
                 @SerializedName("cover_big") val coverBig: String = "",
                 @SerializedName("cover_xl") val coverXl: String = "",
                 val trackList: String = "",
                 val type: String = ""
):Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(cover)
        parcel.writeString(coverSmall)
        parcel.writeString(coverMedium)
        parcel.writeString(coverBig)
        parcel.writeString(coverXl)
        parcel.writeString(trackList)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }

}