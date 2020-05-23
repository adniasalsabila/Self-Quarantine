package org.d3if4133.side_project_adnia.recyclerview

import android.view.View
import org.d3if4133.side_project_adnia.database.Track

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, track: Track)

}