package pt.ipg.food_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pt.ipg.food_app.dataclass.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)

// Database class for managing the meal data using Room.
abstract class MealDatabase : RoomDatabase(){

    // Abstract function to get access to the MealDao.
    abstract fun MealDao():MealDao

    companion object{
        // Volatile variable to ensure thread safety of INSTANCE.
        @Volatile
        var INSTANCE: MealDatabase? = null

        // Synchronized function to get a singleton instance of the database.
        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration().build()

            }
            return INSTANCE as MealDatabase
        }

    }
}