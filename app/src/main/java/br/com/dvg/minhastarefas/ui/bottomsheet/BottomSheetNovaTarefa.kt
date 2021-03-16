package br.com.dvg.minhastarefas.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import br.com.dvg.minhastarefas.R
import br.com.dvg.minhastarefas.model.StatusTarefa
import br.com.dvg.minhastarefas.repository.model.TarefaEntity
import br.com.dvg.minhastarefas.ui.fragments.DialogClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetNovaTarefa : BottomSheetDialogFragment() {

    lateinit var bottomSheetViewModel: BottomSheetViewModel

    lateinit var dialogClickListener: DialogClickListener

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
        return inflater.inflate(R.layout.fragment_nova_tarefa,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btSalvarTarefa = view.findViewById<Button>(R.id.btSalvarTarefa)
        btSalvarTarefa.setOnClickListener {

            val titulo = view.findViewById<EditText>(R.id.edTituloTarefa)
            val descricao = view.findViewById<EditText>(R.id.edDescricaoTarefa)

            val novaTarefa = TarefaEntity(0,
                titulo.text.toString(),
                descricao.text.toString(),
                StatusTarefa.AFAZER.valor)

            //Cadastro banco de dados
            bottomSheetViewModel = ViewModelProviders.of(this).get(BottomSheetViewModel::class.java)
            bottomSheetViewModel.cadastrarTarefa(novaTarefa)

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