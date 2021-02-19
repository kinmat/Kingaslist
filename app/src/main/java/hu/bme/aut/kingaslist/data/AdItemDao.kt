package hu.bme.aut.kingaslist.data

import androidx.room.*

@Dao
interface AdItemDao {
    @Query("SELECT * FROM aditem")
    fun getAll(): List<AdItem>

    @Insert
    fun insert(aditems: AdItem): Long

    @Update
    fun update(aditems: AdItem)

    @Delete
    fun deleteItem(aditems: AdItem)
}