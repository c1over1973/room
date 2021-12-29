package com.example.room.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.data.Note
import kotlinx.android.synthetic.main.custom_row.view.*


class NoteAdapter: RecyclerView.Adapter<NoteAdapter.MyViewHolder>(){

    private var noteList = emptyList<Note>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: NoteAdapter.MyViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.itemView.title.text = currentItem.title.toString()

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToNoteFragment(currentItem)
            holder.itemView.findNavController().navigate(R.id.action_listFragment_to_noteFragment)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(note: List<Note>){
        this.noteList = note
        notifyDataSetChanged()
    }

}