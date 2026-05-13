package campa.david.thecheezery_davidcampa.data.database

import android.content.Context
import campa.david.thecheezery_davidcampa.data.database.entity.ProductComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductEntity
import campa.david.thecheezery_davidcampa.data.database.relation.ComboWithProducts

@Database(
    entities = [
        ProductEntity::class,
        ProductComboEntity::class,
        ComboWithProductsEntity::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun productoDao(): ProductoDao
    abstract fun comboDao(): ComboDao
    abstract fun comboProductoDao(): ComboProductoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                lateinit var instance: AppDatabase

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)


                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}