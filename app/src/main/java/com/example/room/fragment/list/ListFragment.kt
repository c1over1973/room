package com.example.room.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.data.NoteViewModel
import com.example.room.databinding.FragmentListBinding
import com.example.room.fragment.update.UpdateFragmentArgs

class ListFragment : Fragment() {

    private var mbinding: FragmentListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = mbinding!!

    private lateinit var mNoteViewModel: NoteViewModel
    private val args by navArgs<UpdateFragmentArgs>()

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

        //swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        })


        mbinding!!.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_noteFragment)
        }

        //add menu
        setHasOptionsMenu(true)


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.del_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_del){
            deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        val respond = AlertDialog.Builder(requireContext())
        respond.setPositiveButton("是"){_, _ ->
            mNoteViewModel.deleteAllData()
            Toast.makeText(requireContext(), "已清除所有資料!", Toast.LENGTH_SHORT).show()
        }
        respond.setNegativeButton("否"){_, _ -> }
        respond.setTitle("清除所有資料")
        respond.setMessage("是否清除所有資料?")
        respond.create().show()
    }

}