package hva.nl.rockpaperscissors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resultTable")
data class Result(

    @ColumnInfo(name = "playerChoice")
    val playerChoice: String,
    @ColumnInfo(name = "computerChoice")
    val computerChoice: String,
    @ColumnInfo(name = "winner")
    val winner: String,
    @ColumnInfo(name = "dateTime")
    val dateTime: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)