package br.com.dvg.minhastarefas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dvg.minhastarefas.R
import br.com.dvg.minhastarefas.model.Tarefa
import br.com.dvg.minhastarefas.ui.bottomsheet.BottomSheetVisualizarTarefa

class ListaTarefasAdapter(private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ListaTarefasAdapter.ListaTarefaHolder>() {

    var listaTarefas: MutableList<Tarefa> = mutableListOf()

    fun setListData(data: MutableList<Tarefa>) {
        this.listaTarefas = data
    }


    //1- Define qual é o layout do item da RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaTarefaHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_tarefa, parent, false)
        return ListaTarefaHolder(item)
    }

    //2- Inicializa as view
    // Bind das views do layout do Item (item_lista_fragment)
    class ListaTarefaHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titulo: TextView
        val descricao : TextView

        init {
            titulo = view.findViewById(R.id.tvTituloTarefa)
            descricao = view.findViewById(R.id.tvDescricaoTarefa)
        }
    }


    //3- Popula os valores de cada view do layout do item, utilizando a lista enviada
    override fun onBindViewHolder(holder: ListaTarefaHolder, position: Int) {
        holder.titulo.text = listaTarefas[position].titulo
        holder.descricao.text = listaTarefas[position].descricao

        //Carregamento da tela de Visualizar tarefa (BottomSheetDialog)
        holder.itemView.setOnClickListener {

            val tarefa = Tarefa(listaTarefas[position].id,
                listaTarefas[position].titulo,
                listaTarefas[position].descricao,
                listaTarefas[position].status)

            val bottomSheetVisualizarTarefa = BottomSheetVisualizarTarefa.newInstance(tarefa)
            bottomSheetVisualizarTarefa.show(fragmentManager, "Visualizar Detalhes tarefa")
        }

    }

    override fun getItemCount() = listaTarefas.size

}