package com.example.room.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.data.NoteViewModel
import com.example.room.databinding.FragmentListBinding

class ListFragment : Fragment(){


    private lateinit var mNoteViewModel: NoteViewModel
    private val adapter = NoteListAdapter()

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

//        val adapter = NoteListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //itemtouchhelper
        val swipetoDelete = object : SwipeToDelete(requireActivity()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.currentList[viewHolder.adapterPosition]
                mNoteViewModel.deleteNote(note)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipetoDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //NoteViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(requireActivity()){adapter.submitList(it)}


        binding!!.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment2)
        }

        //add menu
        setHasOptionsMenu(true)



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.list_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search event"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    searchData(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null){
                    searchData(query)
                }
                return true
            }

        })
    }

    private fun searchData(query: String){
        val searchQuery = "%$query%"

        mNoteViewModel.searchData(searchQuery).observe(requireActivity()){adapter.submitList(it)}
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_del -> deleteAllData()
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