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

/**
 * Created by Melvin Biamont
 *
 * Reclyclerview adapter to display tracks
 */
class TracksAdapter(val context: Context, private val trackClickedListener: OnTrackClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_TRACK = 1
    private val TYPE_FOOTER = 2

    var trackList = ArrayList<Track>()
    var subtitle = ""
    var allLoaded = false

    /**
     * Reset the track list and add the new given ones
     */
    fun resetTracks(tracks: List<Track>) {
        trackList.clear()
        trackList.addAll(tracks)

        notifyDataSetChanged()
    }

    /**
     * Add given new tracks to the current list
     */
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
        // We add 1 item for the header, and 1 item for the footer (loader) if all data aren't loaded yet
        return trackList.size + 1 + if (!allLoaded) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(subtitle)

            is TrackViewHolder -> holder.bind(trackList[position - 1], context, position - 1)
        }
    }
}

/**
 * Viewholder for the header
 */
class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var lblSubtitle: TextView = itemView.lblSubtitle

    /**
     * Change the subtitle (TOP100 or search value)
     */
    fun bind(subtitle: String) {
        lblSubtitle.text = subtitle
    }
}

/** Viewholder for the footer (loader) */
class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

/**
 * Viewholder for the track
 */
class TrackViewHolder(private val tracksAdapter: TracksAdapter, itemView: View, private val trackClickedListener: OnTrackClickedListener) : RecyclerView.ViewHolder(
        itemView) {

    var lblTitle: TextView = itemView.lblTitle
    var lblArtist: TextView = itemView.lblArtist
    var icCover: ImageView = itemView.icCover

    /**
     * Update view values
     */
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

/**
 * Listener to be awared when the user click on a track
 */
interface OnTrackClickedListener {

    /**
     * Function called when the user click on a track item.
     * We return the whole track list and the position of the item clicked in the track list.
     */
    fun onTrackClicked(trackList: List<Track>, position: Int)
}