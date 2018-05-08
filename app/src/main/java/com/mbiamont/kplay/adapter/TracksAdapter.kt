package com.mbiamont.kplay.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mbiamont.kplay.R
import com.mbiamont.kplay.data.model.Track
import com.mbiamont.kplay.di.GlideApp
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_track.view.*

class TracksAdapter(val context: Context, private val trackClickedListener: OnTrackClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_TRACK = 1
    private val TYPE_FOOTER = 2

    var trackList = ArrayList<Track>()
    var subtitle = ""
    var allLoaded = false

    fun resetTracks(tracks: List<Track>) {
        trackList.clear()
        trackList.addAll(tracks)

        notifyDataSetChanged()
    }

    fun addNewTracks(tracks: List<Track>) {
        allLoaded = tracks.isEmpty()
        trackList.addAll(tracks)

        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else if (allLoaded || position < itemCount - 1) {
            TYPE_TRACK
        } else {
            TYPE_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(LayoutInflater
                                                    .from(parent.context)
                                                    .inflate(R.layout.item_header, parent, false))

            TYPE_TRACK -> TrackViewHolder(this, LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_track, parent, false),
                                          trackClickedListener)

            TYPE_FOOTER -> FooterViewHolder(LayoutInflater
                                                    .from(parent.context)
                                                    .inflate(R.layout.item_loader, parent, false))

            else -> throw RuntimeException(
                    "there is no type that matches the type $viewType + make sure your using types correctly")
        }
    }

    override fun getItemCount(): Int {
        return trackList.size + 1 + if (!allLoaded) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(subtitle)

            is TrackViewHolder -> holder.bind(trackList[position - 1], context, position - 1)
        }
    }
}

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var lblSubtitle: TextView = itemView.lblSubtitle

    fun bind(subtitle: String) {
        lblSubtitle.text = subtitle
    }
}

class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class TrackViewHolder(private val tracksAdapter: TracksAdapter, itemView: View, private val trackClickedListener: OnTrackClickedListener) : RecyclerView.ViewHolder(
        itemView) {

    var lblTitle: TextView = itemView.lblTitle
    var lblArtist: TextView = itemView.lblArtist
    var icCover: ImageView = itemView.icCover

    fun bind(track: Track, context: Context, position: Int) {
        lblTitle.text = track.title
        lblArtist.text = track.artist.name

        itemView.setOnClickListener {
            trackClickedListener.onTrackClicked(tracksAdapter.trackList, position)
        }

        GlideApp
                .with(context)
                .load(track.album.coverMedium)
                .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(10)))
                .placeholder(R.drawable.bg_placeholder_cover)
                .into(icCover)
    }
}

interface OnTrackClickedListener {

    fun onTrackClicked(trackList: List<Track>, position: Int)
}