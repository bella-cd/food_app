package pt.ipg.food_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ipg.food_app.dataclass.Meal

@Dao
interface MealDao {

    // Inserts or updates a meal in the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    // Deletes a meal from the database.
    @Delete
    suspend fun delete(meal: Meal)

    // Retrieves all meals from the "mealInformation" table as LiveData.
    @Query ("SELECT * FROM mealInformation")
      fun getAllMeals():LiveData<List<Meal>>

}