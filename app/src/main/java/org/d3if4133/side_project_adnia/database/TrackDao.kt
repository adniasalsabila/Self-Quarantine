package org.d3if4133.side_project_adnia.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TrackDao {

    @Insert
    fun insert(track: Track)

    @Update
    fun update(track: Track)

    @Query("SELECT * FROM quarantine")
    fun getTrack(): LiveData<List<Track>>

    @Query("DELETE FROM quarantine")
    fun clear()

    @Query("DELETE FROM quarantine WHERE id = :quarantineId")
    fun delete(quarantineId: Long)

}