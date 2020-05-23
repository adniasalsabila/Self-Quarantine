package org.d3if4133.side_project_adnia.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if4133.side_project_adnia.R
import org.d3if4133.side_project_adnia.database.Track
import org.d3if4133.side_project_adnia.databinding.RecyclerviewTrackBinding
import org.d3if4133.side_project_adnia.utils.convertLongToDateString

class TrackAdapter(private val track: List<Track>) : RecyclerView.Adapter<TrackAdapter.DiaryViewHolder>() {
    var listener: RecyclerViewClickListener? = null

    inner class DiaryViewHolder(
        val recyclerviewDiaryBinding: RecyclerviewTrackBinding
    ) : RecyclerView.ViewHolder(recyclerviewDiaryBinding.root)

    override fun getItemCount() = track.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiaryViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recyclerview_track, parent, false)
    )

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.recyclerviewDiaryBinding.tvDate.text = convertLongToDateString(track[position].tanggal)
        holder.recyclerviewDiaryBinding.tvMessage.text = track[position].message

        holder.recyclerviewDiaryBinding.listTrack.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, track[position])
        }
    }
}