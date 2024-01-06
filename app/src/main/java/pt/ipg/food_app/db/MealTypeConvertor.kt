package pt.ipg.food_app.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

// Type converter class for Room to handle conversion between Any? and String.
@TypeConverters
class MealTypeConvertor {

    // Convert Any? to String
    @TypeConverter
    fun fromAnyToString(attribute:Any?): String{
        if(attribute == null)
            return  ""
        return attribute as String
    }

    // Convert String to Any
    @TypeConverter
    fun fromStringToAny (attribute: String?) : Any{
        if(attribute == null)
            return  ""
        return attribute
    }

}