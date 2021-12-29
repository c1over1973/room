package com.example.room.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.R
import com.example.room.data.NoteViewModel
import com.example.room.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var mbinding: FragmentListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = mbinding!!

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        //Recyclerview
        val adapter = NoteAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //NoteViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(viewLifecycleOwner, {note -> adapter.setData(note)})


        mbinding!!.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_noteFragment)
        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }

}