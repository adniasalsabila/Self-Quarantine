package org.d3if4133.side_project_adnia.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase

import org.d3if4133.side_project_adnia.R
import org.d3if4133.side_project_adnia.TampungFragment
import org.d3if4133.side_project_adnia.database.Track
import org.d3if4133.side_project_adnia.database.TrackDatabase
import org.d3if4133.side_project_adnia.databinding.FragmentTambahTrackBinding
import org.d3if4133.side_project_adnia.viewmodel.TrackViewModel
import org.d3if4133.side_project_adnia.viewmodel.TrackViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class TambahTrackFragment : Fragment() {

    private lateinit var binding: FragmentTambahTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tambah_track, container, false)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_button, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application
        val dataSource = TrackDatabase.getInstance(application).TrackDao
        val viewModelFactory = TrackViewModelFactory(dataSource, application)
        val trackViewModel = ViewModelProvider(this, viewModelFactory).get(TrackViewModel::class.java)

        return when(item.itemId) {
            R.id.item_action -> {
                if (inputCheck(trackViewModel)) {
                    requireView().findNavController().popBackStack()
                    Toast.makeText(requireContext(), R.string.success_insert, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), R.string.null_message, Toast.LENGTH_SHORT).show()
                }
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun inputCheck(trackViewModel: TrackViewModel): Boolean {
        return when {
            binding.etDiary.text.trim().isEmpty() -> false
            else -> {
                doInsert(trackViewModel)
                true
            }
        }
    }

    private fun doInsert(trackViewModel: TrackViewModel) {
        val message = binding.etDiary.text.toString()
        trackViewModel.onClickInsert(message)
    }

    private fun judul() {
        val getActivity = activity!! as TampungFragment
        getActivity.supportActionBar?.title = "Self Quarantine"
    }

}
