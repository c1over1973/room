package com.example.room.fragment.update

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
import com.example.room.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {
    private var mbinding: FragmentUpdateBinding? = null
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
        mbinding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.updTitle.setText(args.currentNote.title)
        binding.updContent.setText(args.currentNote.content)

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
            UpdateData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun UpdateData(){
        val title = binding.updTitle.text.toString()
        val content = binding.updContent.text.toString()

        if (inputcheck(title, content)){
            val note = Note(title, content,args.currentNote.id)
            mNoteViewModel.updateNote(note)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "已完成修改!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(), "請確認是否輸入完整!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputcheck(title: String, content: String): Boolean{
        return !title.isEmpty() && !content.isEmpty()
    }


}