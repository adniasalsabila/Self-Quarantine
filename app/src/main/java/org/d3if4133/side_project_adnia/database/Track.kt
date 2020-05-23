package org.d3if4133.side_project_adnia.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quarantine")
data class Track (
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,

    @ColumnInfo(name = "message")
    var message:String,

    @ColumnInfo(name = "tanggal")
    val tanggal:Long = System.currentTimeMillis()
)