package com.mbiamont.kplay.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Artist(val id: Int = 0,
             val name: String = "",
             val link: String = "",
             val preview: String = "",
             val picture: String = "",
             @SerializedName("picture_small") val pictureSmall: String = "",
             @SerializedName("picture_medium") val pictureMedium: String = "",
             @SerializedName("picture_big") val pictureBig: String = "",
             @SerializedName("picture_xl") val pictureXl: String = "",
             val radio: Boolean = false,
             val trackList: String = "",
             val type: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(link)
        parcel.writeString(preview)
        parcel.writeString(picture)
        parcel.writeString(pictureSmall)
        parcel.writeString(pictureMedium)
        parcel.writeString(pictureBig)
        parcel.writeString(pictureXl)
        parcel.writeByte(if (radio) 1 else 0)
        parcel.writeString(trackList)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }
}