package campa.david.thecheezery_davidcampa.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import campa.david.thecheezery_davidcampa.data.database.dao.CombosDao
import campa.david.thecheezery_davidcampa.data.database.dao.ProductComboDao
import campa.david.thecheezery_davidcampa.data.database.dao.ProductDao
import campa.david.thecheezery_davidcampa.data.database.entity.ComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductEntity
import campa.david.thecheezery_davidcampa.data.database.relation.ComboWithProducts
import campa.david.thecheezery_davidcampa.domain.ProductType
import campa.david.thecheezery_davidcampa.data.database.AppDatabase.AppDatabaseConverters

@Database(
    entities = [
        ProductEntity::class,
        ComboEntity::class,
        ProductComboEntity::class,
        ComboWithProductsEntity::class
],
version = 1,
exportSchema = false,
)
@TypeConverters(AppDatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun productDao(): ProductDao
    abstract fun combosDao(): CombosDao
    abstract fun productComboDao(): ProductComboDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery_db",
                )

                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
    class AppDatabaseConverters {
        @TypeConverter
        fun fromProductType(type: ProductType): String = type.name

        @TypeConverter
        fun toProductType(value: String): ProductType = ProductType.valueOf(value)
    }
}