package org.d3if4133.side_project_adnia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.d3if4133.side_project_adnia.databinding.ActivityMainBinding
import org.d3if4133.side_project_adnia.viewmodel.CoronaViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CoronaViewModel

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    var adapter2: RecyclerView.Adapter<RecycleAdapterReq.ViewHolder>? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        ReminderWorker.runAt()


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(CoronaViewModel::class.java)

        viewModel.getDataCorona().observe(this, Observer { indoData ->
            if (indoData != null) {
                binding.tvKonfirmasi.text = indoData[0].positif
                binding.tvSembuh.text = indoData[0].sembuh
                binding.tvMeninggal.text = indoData[0].meninggal
            }
        })
        viewModel.setDataIndonesia()
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_req.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = RecyclerAdapter()
        recycler_view.adapter = adapter

        adapter2 = RecycleAdapterReq()
        recycler_view_req.adapter = adapter2
//
        binding.btStarted.setOnClickListener {
            startActivity(Intent(this, TampungFragment::class.java))
        }

        binding.tvViewAll.setOnClickListener {
            startActivity(Intent(this, Gejala::class.java))
        }

//        val navController = this.findNavController(R.id.mainFragment)
//        NavigationUI.setupActionBarWithNavController(this, navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.mainFragment)
//        return navController.navigateUp()
//    }


}


