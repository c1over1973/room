package com.example.room.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    val title: String,
    val content: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int
):Parcelable{
}