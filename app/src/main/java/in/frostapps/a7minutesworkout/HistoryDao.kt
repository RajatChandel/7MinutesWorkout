package `in`.frostapps.a7minutesworkout

import `in`.frostapps.a7minutesworkout.data.HistoryEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `history-table`")
    fun fetchAllHistory() : Flow<List<HistoryEntity>>
}