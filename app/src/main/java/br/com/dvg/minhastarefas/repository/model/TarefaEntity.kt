package br.com.dvg.minhastarefas.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.dvg.minhastarefas.repository.sqlite.*

@Entity(tableName = TABLE_TAREFA)
data class TarefaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0L,
    var titulo: String = "",
    var descricao: String = "",
    var statusTarefa: String = ""
)