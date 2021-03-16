package br.com.dvg.minhastarefas.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dvg.minhastarefas.repository.model.TarefaEntity
import br.com.dvg.minhastarefas.repository.sqlite.DATABASE_NAME
import br.com.dvg.minhastarefas.repository.sqlite.DATABASE_VERSION

@Database(entities = [TarefaEntity::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tarefasDao(): TarefaDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {

            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext, AppDatabase::class.java, DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}