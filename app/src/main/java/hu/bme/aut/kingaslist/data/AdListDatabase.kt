package hu.bme.aut.kingaslist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AdItem::class], version = 3)
@TypeConverters(value = [AdItem.Category::class])
abstract class AdListDatabase : RoomDatabase() {
    abstract fun AdItemDao(): AdItemDao
}
