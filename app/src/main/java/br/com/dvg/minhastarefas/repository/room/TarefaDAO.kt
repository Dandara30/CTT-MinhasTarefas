package br.com.dvg.minhastarefas.repository.room

import androidx.room.*
import br.com.dvg.minhastarefas.repository.model.TarefaEntity
import br.com.dvg.minhastarefas.repository.sqlite.TABLE_TAREFA

@Dao
interface TarefaDAO {

    @Insert
    fun inserir(tarefa: TarefaEntity)

    @Update
    fun editar(tarefa: TarefaEntity)

    @Delete
    fun excluir(tarefa: TarefaEntity)

//    @Query("SELECT * FROM $TABLE_TAREFA WHERE $COLUMN_TITULO LIKE :titulo ORDER BY $COLUMN_TITULO")
//    fun pesquisar(titulo: String): LiveData<TarefaEntity>

    @Query("SELECT * FROM $TABLE_TAREFA WHERE statusTarefa LIKE :statusTarefa")
    fun listarTarefasPorStatus(statusTarefa: String): List<TarefaEntity>

}