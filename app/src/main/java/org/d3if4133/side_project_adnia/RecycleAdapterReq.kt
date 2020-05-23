package org.d3if4133.side_project_adnia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapterReq : RecyclerView.Adapter<RecycleAdapterReq.ViewHolder>() {

    // Daftar gambar yang sudah disimpan di folder drawable
    private val images_req = intArrayOf(
        R.drawable.fever,
        R.drawable.sneeze,
        R.drawable.fatigue
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView


        init {
            itemImage = itemView.findViewById(R.id.img_viewReq)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):
            ViewHolder {

        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.requirements, viewGroup, false)


        return ViewHolder(v)


    }



    override fun getItemCount(): Int {
        return images_req.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.itemImage.setImageResource(images_req[i])
    }


}