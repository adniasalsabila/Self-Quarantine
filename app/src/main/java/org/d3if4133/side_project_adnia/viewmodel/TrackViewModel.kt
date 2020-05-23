package org.d3if4133.side_project_adnia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import org.d3if4133.side_project_adnia.database.Track
import org.d3if4133.side_project_adnia.database.TrackDao

class TrackViewModel (
    val database: TrackDao,
    application: Application
) : AndroidViewModel(application) {

    // buat viewModelJob dan UiScope
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    // buat variabel track dan isi dengan data track
    val track = database.getTrack()

    // fungsi insert untuk dipanggil di fragment tambah track
    fun onClickInsert(message: String) {
        uiScope.launch {
            val track = Track(0, message)
            // panggil fungsi insert yang di buat dibawah fungsi onClickInsert
            insert(track)
        }
    }

    // buat suspend fun insert untuk insert data ke roomDB
    private suspend fun insert(track: Track) {
        withContext(Dispatchers.IO) {
            database.insert(track)
        }
    }

    fun onClickClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onClickUpdate(track: Track) {
        uiScope.launch {
            update(track)
        }
    }

    private suspend fun update(track: Track) {
        withContext(Dispatchers.IO) {
            database.update(track)
        }
    }

    fun onClickDelete(quarantineId: Long) {
        uiScope.launch {
            delete(quarantineId)
        }
    }

    private suspend fun delete(quarantineId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(quarantineId)
        }
    }
}