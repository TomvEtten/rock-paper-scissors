package hva.nl.rockpaperscissors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class ResultRoomDatabase : RoomDatabase() {

    abstract fun resultDao() : ResultDao

    companion object {
        private const val DATABASE_NAME = "RESULT_DATABASE"

        @Volatile
        private var resultRoomDatabaseInstance: ResultRoomDatabase? = null

        fun getDatabase(context: Context): ResultRoomDatabase? {
            if (resultRoomDatabaseInstance != null) {
                return resultRoomDatabaseInstance
            }
            synchronized(ResultRoomDatabase::class.java) {
                resultRoomDatabaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return resultRoomDatabaseInstance;

        }
    }

}