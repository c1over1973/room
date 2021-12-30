package com.example.room.fragment.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.room.R
import com.example.room.data.Note
import com.example.room.data.NoteViewModel
import com.example.room.databinding.FragmentNoteBinding
import kotlinx.android.synthetic.main.fragment_note.view.*

class noteFragment : Fragment() {

    private var mbinding: FragmentNoteBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = mbinding!!

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding = FragmentNoteBinding.inflate(inflater, container, false)
        val view = binding.root

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)


        //add menu
        setHasOptionsMenu(true)


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save){
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase(){
        val title = binding.editTitle.text.toString()
        val content = binding.editContent.text.toString()

        if (inputcheck(title, content)){
            val note = Note(title, content,0)
            mNoteViewModel.addNote(note)
            findNavController().navigate(R.id.action_noteFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "請確認是否輸入完整!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputcheck(title: String, content: String): Boolean{
        return !title.isEmpty() && !content.isEmpty()
    }
}