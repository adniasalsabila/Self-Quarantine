package org.d3if4133.side_project_adnia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // daftar title yang akan ditampilkan di card
    private val titles = arrayOf(
        "Isolasi Mandiri",
        "Jangan Keluar Rumah",
        "Hubungi Pihak RS",
        "Jangan Dijenguk",
        "Hindari Kontak Langsung",
        "Jangan berbagi peralatan",
        "Pakai Masker",
        "Gunakan tissue"
    )
    // daftar teks yang akan ditampilkan juga
    private val details = arrayOf(
        "tinggal terpisah dari orang lain",
        "kecuali untuk pengobatan",
        "jika ingin periksa",
        "hingga bernar sembuh",
        "dengan orang sakit",
        "terutama alat makan",
        "Terutama jika bertemu orang",
        "ketika bersin lalu buang"
    )
    // Daftar gambar yang sudah disimpan di folder drawable
    private val images = intArrayOf(
        R.drawable.gambar_1,
        R.drawable.gambar_2,
        R.drawable.gambar_3,
        R.drawable.gambar_4,
        R.drawable.gambar_5,
        R.drawable.gambar_6,
        R.drawable.gambar_7,
        R.drawable.gambar_8
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.img_view)
            itemTitle = itemView.findViewById(R.id.txt_highFever)
            itemDetail = itemView.findViewById(R.id.txt_detail)

//            itemView.setOnClickListener { v: View  ->
//                R.layout.requirements
//            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):
            ViewHolder {

        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.symptoms, viewGroup, false)

        return ViewHolder(v)

    }



    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])
    }




}