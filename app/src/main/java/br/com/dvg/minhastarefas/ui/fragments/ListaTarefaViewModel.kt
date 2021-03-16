package br.com.dvg.minhastarefas.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.dvg.minhastarefas.model.StatusTarefa
import br.com.dvg.minhastarefas.repository.model.TarefaEntity
import br.com.dvg.minhastarefas.repository.room.AppDatabase
import br.com.dvg.minhastarefas.repository.room.TarefaDAO

class ListaTarefaViewModel(app: Application): AndroidViewModel(app) {

    lateinit var tarefasAFazer : MutableLiveData<List<TarefaEntity>>
    lateinit var tarefasEmProgresso : MutableLiveData<List<TarefaEntity>>
    lateinit var tarefasFeitas : MutableLiveData<List<TarefaEntity>>

    init{

        tarefasAFazer = MutableLiveData()
        tarefasEmProgresso = MutableLiveData()
        tarefasFeitas = MutableLiveData()
        getTarefasAFazer()
    }

    fun getTarefasAFazer() {

        val tarefaDAO : TarefaDAO? = AppDatabase.getAppDatabase(getApplication())?.tarefasDao()
        val listaTarefaAFazer = tarefaDAO?.listarTarefasPorStatus(StatusTarefa.AFAZER.valor)
        this.tarefasAFazer.postValue(listaTarefaAFazer)
    }

    fun getTarefasEmProgresso() {

        val tarefaDAO : TarefaDAO? = AppDatabase.getAppDatabase(getApplication())?.tarefasDao()
        val listaTarefaEmProgresso = tarefaDAO?.listarTarefasPorStatus(StatusTarefa.EMPROGRESSO.valor)
        tarefasEmProgresso.postValue(listaTarefaEmProgresso)
    }

    fun getTarefasFeitas() {

        val tarefaDAO : TarefaDAO? = AppDatabase.getAppDatabase(getApplication())?.tarefasDao()
        val listaTarefaFeitas = tarefaDAO?.listarTarefasPorStatus(StatusTarefa.FEITA.valor)
        tarefasFeitas.postValue(listaTarefaFeitas)
    }

    fun getTarefasAFazerObservers(): MutableLiveData<List<TarefaEntity>> {
        getTarefasAFazer()
        return this.tarefasAFazer
    }

    fun getTarefasEmProgressoObservers(): MutableLiveData<List<TarefaEntity>> {
        getTarefasEmProgresso()
        return tarefasEmProgresso
    }


    fun getTarefasFeitasObservers(): MutableLiveData<List<TarefaEntity>> {
        getTarefasFeitas()
        return tarefasFeitas
    }

}