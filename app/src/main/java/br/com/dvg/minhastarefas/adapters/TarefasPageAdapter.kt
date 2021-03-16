package br.com.dvg.minhastarefas.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.dvg.minhastarefas.ui.fragments.ListaTarefasAFazerFragment
import br.com.dvg.minhastarefas.ui.fragments.ListaTarefasEmProgressoFragment
import br.com.dvg.minhastarefas.ui.fragments.ListaTarefasFeitaFragment

class TarefasPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    /*
    Retorna o número de fragments a serem colocadas
     */
    override fun getCount(): Int {
        return 3
    }

    /*
    Relaciona o Item (ABA) com o Fragment a ser exibido
     */
    override fun getItem(position: Int): Fragment {
        return when(position) {
            //Verifica a posicao clicada e carrega o Fragment da posicao
            0 -> ListaTarefasAFazerFragment()
            1 -> ListaTarefasEmProgressoFragment()
            2 -> ListaTarefasFeitaFragment()
            else -> getItem(position)
        }
    }

    // Titulo de cada Page/Aba
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "A fazer"
            1 -> "Em progresso"
            2 -> "Feitas"
            else -> super.getPageTitle(position)

        }
    }
}