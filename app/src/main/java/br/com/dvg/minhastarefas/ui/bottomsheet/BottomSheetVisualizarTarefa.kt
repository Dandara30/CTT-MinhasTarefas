package br.com.dvg.minhastarefas.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import br.com.dvg.minhastarefas.R
import br.com.dvg.minhastarefas.model.StatusTarefa
import br.com.dvg.minhastarefas.model.Tarefa
import br.com.dvg.minhastarefas.repository.model.TarefaEntity
import br.com.dvg.minhastarefas.ui.fragments.DialogClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_visualizar_tarefa.*

class BottomSheetVisualizarTarefa : BottomSheetDialogFragment() {

    lateinit var bottomSheetViewModel: BottomSheetViewModel
    lateinit var dialogClickListener: DialogClickListener

    private lateinit var titulo: String
    private lateinit var statusTarefa: StatusTarefa
    private lateinit var descricao: String
    private lateinit var dadosTarefa: Tarefa

    companion object {

        fun newInstance(tarefa: Tarefa) = BottomSheetVisualizarTarefa().apply {
            this.titulo = tarefa.titulo
            this.descricao = tarefa.descricao
            this.statusTarefa = tarefa.status
            this.dadosTarefa = tarefa
        }
    }


    //Callback tratando o comportamento de Dialog Colapsado
    private val bottomSheetBehaviorCallback = object: BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            //Nothing
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)

        try {
            dialogClickListener = parentFragment as DialogClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Erro ao chamar fragment que implemeta DialogClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Inflate do layout
        return inflater.inflate(R.layout.fragment_visualizar_tarefa,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloView = view.findViewById<TextView>(R.id.tvTituloTarefa)
        val statusTarefaView = view.findViewById<TextView>(R.id.tvStatusTarefa)
        val descricaoView = view.findViewById<TextView>(R.id.tvDescricaoTarefa)
        val botaoEditar = view.findViewById<ImageButton>(R.id.btEditarTarefa)
        val botaoSalvarTarefa = view.findViewById<Button>(R.id.btSalvarTarefa)

        tituloView.text = titulo
        statusTarefaView.text = statusTarefa.valor
        descricaoView.text = descricao


        //Verificando o status da Tarefa para mudar label do botão
        when (statusTarefa.name) {
            "AFAZER" -> botaoSalvarTarefa.text = resources.getString(R.string.label_iniciar_tarefa)
            "EMPROGRESSO" -> botaoSalvarTarefa.text =
                resources.getString(R.string.label_finalizar_tarefa)
            "FEITA" -> botaoSalvarTarefa.visibility = View.GONE
        }

        botaoEditar.setOnClickListener {

            //Abrir Dialog de Tela de Edição
            val bottomSheetEditarTarefa = BottomSheetEditarTarefa(dadosTarefa, dialogClickListener)
            bottomSheetEditarTarefa.show(childFragmentManager, "Editar Tarefa Dialog")

        }

        val btExcluir = view.findViewById<Button>(R.id.btExcluirTarefa)
        btExcluir.setOnClickListener {
            bottomSheetViewModel = ViewModelProviders.of(this).get(BottomSheetViewModel::class.java)
            val entity = TarefaEntity(dadosTarefa.id, dadosTarefa.titulo, dadosTarefa.descricao, dadosTarefa.status.valor)
            bottomSheetViewModel.excluirTarefa(entity, dadosTarefa.status)

            dialogClickListener.closeDialog()
            dismiss()

        }

        btSalvarTarefa.setOnClickListener {

            val novoStatusTarefa: StatusTarefa
            val msgUsuario: String
            if (dadosTarefa.status == StatusTarefa.AFAZER) {
                novoStatusTarefa = StatusTarefa.EMPROGRESSO
                msgUsuario = "Tarefa movida para Em progresso!"
            } else {
                novoStatusTarefa = StatusTarefa.FEITA
                msgUsuario = "Tarefa movida para Feita!"
            }


            bottomSheetViewModel = ViewModelProviders.of(this).get(BottomSheetViewModel::class.java)
            val entity = TarefaEntity(dadosTarefa.id, dadosTarefa.titulo, dadosTarefa.descricao, novoStatusTarefa.valor)
            bottomSheetViewModel.editarTarefa(entity)

            Toast.makeText(context, msgUsuario, Toast.LENGTH_LONG).show()

            dialogClickListener.closeDialog()
            dismiss()
        }

        globalLayoutListener(view)
    }


    //Listener de layout do dialog, onde iremos definir o state, peekHeight e tratar estado colpsado
    private fun globalLayoutListener(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {

            val layoutParent = view.parent as View
            layoutParent.setPadding(0,0,0,0)

            val behavior = getBottomSheetBehavior(view)
            if (behavior != null) {
                //Chamando callback para tratar dialog no estado de colapsado
                behavior.setBottomSheetCallback(bottomSheetBehaviorCallback)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 0
            }
        }
    }

    private fun getBottomSheetBehavior(view: View): BottomSheetBehavior<*>? {
        if(view != null) {
            val parent = view.parent as View
            val layoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior

            if (behavior is BottomSheetBehavior<*>) return behavior
        }

        return null

    }

}