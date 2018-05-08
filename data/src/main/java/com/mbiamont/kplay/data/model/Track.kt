package com.mbiamont.kplay.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Track(val id: Long = 0,
            val title: String = "",
            @SerializedName("title_short") val titleShort: String = "",
            @SerializedName("title_version") val titleVersion: String = "",
            val link: String = "",
            val duration: Int = 0,
            val rank: Int = 0,
            @SerializedName("explicit_lyrics") val explicitLyrics: Boolean = false,
            val preview: String = "",
            val position: Int = 0,
            val artist: Artist,
            val album: Album,
            val type: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readParcelable(Artist::class.java.classLoader),
            parcel.readParcelable(Album::class.java.classLoader),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(titleShort)
        parcel.writeString(titleVersion)
        parcel.writeString(link)
        parcel.writeInt(duration)
        parcel.writeInt(rank)
        parcel.writeByte(if (explicitLyrics) 1 else 0)
        parcel.writeString(preview)
        parcel.writeInt(position)
        parcel.writeParcelable(artist, flags)
        parcel.writeParcelable(album, flags)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }
}