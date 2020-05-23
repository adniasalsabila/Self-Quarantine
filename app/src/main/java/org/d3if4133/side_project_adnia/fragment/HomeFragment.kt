package org.d3if4133.side_project_adnia.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import org.d3if4133.side_project_adnia.R
import org.d3if4133.side_project_adnia.TampungFragment
import org.d3if4133.side_project_adnia.database.Track
import org.d3if4133.side_project_adnia.database.TrackDatabase
import org.d3if4133.side_project_adnia.databinding.FragmentHomeBinding
import org.d3if4133.side_project_adnia.recyclerview.RecyclerViewClickListener
import org.d3if4133.side_project_adnia.recyclerview.TrackAdapter
import org.d3if4133.side_project_adnia.viewmodel.TrackViewModel
import org.d3if4133.side_project_adnia.viewmodel.TrackViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), RecyclerViewClickListener{

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = TrackDatabase.getInstance(application).TrackDao
        val viewModelFactory = TrackViewModelFactory(dataSource, application)
        val trackViewModel =
            ViewModelProvider(this, viewModelFactory).get(TrackViewModel::class.java)

          trackViewModel.track.observe(viewLifecycleOwner, Observer {
            val adapter = TrackAdapter(it)
            val recyclerView = binding.rvDiary
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

            adapter.listener = this
        })

        binding.fabTulisDiary.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_tambahTrackFragment)
        }

    }

    override fun onRecyclerViewItemClicked(view: View, track: Track) {

        when (view.id) {
            R.id.list_track -> {
                val bundle = Bundle()
                bundle.putLong("id", track.id)
                bundle.putString("message", track.message)
                view.findNavController()
                    .navigate(R.id.action_homeFragment_to_editTrack, bundle)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application
        val dataSource = TrackDatabase.getInstance(application).TrackDao
        val viewModelFactory = TrackViewModelFactory(dataSource, application)
        val diaryViewModel =
            ViewModelProvider(this, viewModelFactory).get(TrackViewModel::class.java)

        return when (item.itemId) {
            R.id.hapus_track -> {
                diaryViewModel.onClickClear()
                Toast.makeText(requireContext(), R.string.success_remove, Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.theme -> {
                if (isDarkModeOn()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    startActivity(Intent(requireContext(), TampungFragment::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    startActivity(Intent(requireContext(), TampungFragment::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                }
                true
            }
            else -> super.onOptionsItemSelected(item)

//            R.id.lihat_symp -> {
//                val bundle = Bundle()
//                view?.findNavController()
//                    ?.navigate(R.id.action_homeFragment_to_symptomsFragment, bundle)
//                return true
//            }
        }
    }

    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun judul() {
        val getActivity = activity!! as TampungFragment
        getActivity.supportActionBar?.title = "Track Quarantine"
    }
}