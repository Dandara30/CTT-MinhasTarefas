package br.com.dvg.minhastarefas.ui.bottomsheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.dvg.minhastarefas.model.StatusTarefa
import br.com.dvg.minhastarefas.repository.model.TarefaEntity
import br.com.dvg.minhastarefas.repository.room.AppDatabase
import br.com.dvg.minhastarefas.repository.room.TarefaDAO
import br.com.dvg.minhastarefas.ui.fragments.ListaTarefaViewModel

class BottomSheetViewModel(app: Application): AndroidViewModel(app) {

    //ViewModel
    val listaTarefaViewModel = ListaTarefaViewModel(getApplication())

    fun cadastrarTarefa(tarefa: TarefaEntity) {
        val tarefaDAO : TarefaDAO? = AppDatabase.getAppDatabase(getApplication())?.tarefasDao()
        tarefaDAO?.inserir(tarefa)
        listaTarefaViewModel.getTarefasAFazer()

    }

    fun editarTarefa(tarefa: TarefaEntity) {
        val tarefaDAO : TarefaDAO? = AppDatabase.getAppDatabase(getApplication())?.tarefasDao()
        tarefaDAO?.editar(tarefa)
        atualizarListaDoStatus(tarefa.statusTarefa)

    }

    fun excluirTarefa(tarefa: TarefaEntity, statusAtual: StatusTarefa) {
        val tarefaDAO : TarefaDAO? = AppDatabase.getAppDatabase(getApplication())?.tarefasDao()
        tarefaDAO?.excluir(tarefa)
        atualizarListaDoStatus(statusAtual.valor)

    }

    private fun atualizarListaDoStatus(statusAtual: String) {
        when(statusAtual) {
            StatusTarefa.AFAZER.valor -> listaTarefaViewModel.getTarefasAFazer()
            StatusTarefa.EMPROGRESSO.valor -> listaTarefaViewModel.getTarefasEmProgresso()
            StatusTarefa.FEITA.valor -> listaTarefaViewModel.getTarefasFeitas()
        }
    }


}