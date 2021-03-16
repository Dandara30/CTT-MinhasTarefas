package br.com.dvg.minhastarefas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dvg.minhastarefas.R
import br.com.dvg.minhastarefas.adapters.ListaTarefasAdapter
import br.com.dvg.minhastarefas.model.StatusTarefa
import br.com.dvg.minhastarefas.model.Tarefa
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ListaTarefasEmProgressoFragment: Fragment(), DialogClickListener {

    //Views
    private lateinit var rvListaTarefa: RecyclerView
    private lateinit var areaMsgVazia: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var msgListaVazia: TextView

    //ViewModel
    lateinit var listaTarefaViewModel: ListaTarefaViewModel

    //Adapter
    lateinit var recyclerViewAdapter: ListaTarefasAdapter

    private fun inicializaTarefaEmProgresso() {
        val listaTarefas: MutableList<Tarefa> = mutableListOf()
        listaTarefaViewModel = ViewModelProviders.of(this).get(ListaTarefaViewModel::class.java)
        listaTarefaViewModel.getTarefasEmProgressoObservers().observe(viewLifecycleOwner, Observer {

            for (indice in it.indices) {

                val tarefaView = Tarefa(
                    it[indice].id,
                    it[indice].titulo,
                    it[indice].descricao,
                    StatusTarefa.EMPROGRESSO
                )
                listaTarefas.add(tarefaView)

            }
            progressBar.visibility = View.GONE


            if (listaTarefas.size > 0) {
                recyclerViewAdapter.setListData(listaTarefas)
                recyclerViewAdapter.notifyDataSetChanged()
            } else {
                carregarMensagemListaVazia()
            }

        })

//        listaTarefas.add(Tarefa(10, "Teste1", "Descricao 1", StatusTarefa.EMPROGRESSO))
//        listaTarefas.add(Tarefa(11, "Teste2", "Descricao 2", StatusTarefa.EMPROGRESSO))
//        listaTarefas.add(Tarefa(12, "Teste3", "Descricao 3", StatusTarefa.EMPROGRESSO))
//        listaTarefas.add(Tarefa(13, "Teste4", "Descricao 4", StatusTarefa.EMPROGRESSO))
//        listaTarefas.add(Tarefa(14, "Teste5", "Descricao 5", StatusTarefa.EMPROGRESSO))
    }

    //onCreate do fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Inflate do layout do fragment
        return inflater.inflate(R.layout.fragment_lista_tarefas,container,false)
    }



    //após o fragment ser criado
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bind progressBar
        progressBar = view.findViewById(R.id.progressBar)

        //Mensagem usuario
        areaMsgVazia = view.findViewById(R.id.area_msg_vazia)
        msgListaVazia = view.findViewById<TextView>(R.id.tvMsgVaziaDescricao)

        //bind do floating action button
        val fabAdicionar = view.findViewById<ExtendedFloatingActionButton>(R.id.fabAdicionar)
        fabAdicionar.visibility = View.GONE


        //Bind Recyclerview
        rvListaTarefa = view.findViewById(R.id.listaTarefas)
        recyclerViewAdapter = ListaTarefasAdapter(childFragmentManager)
        rvListaTarefa.layoutManager = LinearLayoutManager(requireContext())
        rvListaTarefa.adapter = recyclerViewAdapter

        progressBar.visibility = View.VISIBLE
        inicializaTarefaEmProgresso()

    }


    private fun carregarMensagemListaVazia() {
        rvListaTarefa.visibility = View.GONE //Remove a recyclerview
        areaMsgVazia.visibility = View.VISIBLE //Visivel a msg vazia
        msgListaVazia.text = resources.getString(R.string.msg_vazia_emprogresso)
    }


    override fun closeDialog() {
        progressBar.visibility = View.VISIBLE
        inicializaTarefaEmProgresso()
    }

}
