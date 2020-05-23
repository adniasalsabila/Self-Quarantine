package org.d3if4133.side_project_adnia.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import org.d3if4133.side_project_adnia.R
import org.d3if4133.side_project_adnia.TampungFragment
import org.d3if4133.side_project_adnia.database.Track
import org.d3if4133.side_project_adnia.database.TrackDatabase
import org.d3if4133.side_project_adnia.databinding.FragmentEditTrackBinding
import org.d3if4133.side_project_adnia.viewmodel.TrackViewModel
import org.d3if4133.side_project_adnia.viewmodel.TrackViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class EditTrack : Fragment() {

    private lateinit var binding: FragmentEditTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_track, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null) {
            val message = arguments!!.getString("message")

            // tampilkan message pada edit text
            binding.etDiaryUpdate.setText(message)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_button, menu)
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val application = requireNotNull(this.activity).application
        val dataSource = TrackDatabase.getInstance(application).TrackDao
        val viewModelFactory = TrackViewModelFactory(dataSource, application)
        val diaryViewModel = ViewModelProvider(this, viewModelFactory).get(TrackViewModel::class.java)

        return when (item.itemId) {
            R.id.item_action -> {
                if (inputCheck(arguments!!.getLong("id"), diaryViewModel)) {
                    requireView().findNavController().popBackStack()
                    Toast.makeText(requireContext(), R.string.success_update, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), R.string.null_message, Toast.LENGTH_SHORT).show()
                }
                return true
            }

            R.id.hapus_track -> {
                diaryViewModel.onClickDelete(arguments!!.getLong("id"))
                requireView().findNavController().popBackStack()
                Toast.makeText(requireContext(), R.string.success_remove, Toast.LENGTH_SHORT).show()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun inputCheck(id: Long, diaryViewModel: TrackViewModel): Boolean {

        return when {
            binding.etDiaryUpdate.text.trim().isEmpty() -> false

            else -> {
                doUpdate(id, diaryViewModel)
                true
            }
        }
    }

    private fun doUpdate(id: Long, diaryViewModel: TrackViewModel) {
        val message = binding.etDiaryUpdate.text.toString()
        val date = System.currentTimeMillis()
        val track = Track(id, message, date)

        diaryViewModel.onClickUpdate(track)
    }

    private fun judul() {
        val getActivity = activity!! as TampungFragment
        getActivity.supportActionBar?.title = "Update Track"
    }

}
