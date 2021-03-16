package br.com.dvg.minhastarefas.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import br.com.dvg.minhastarefas.R
import br.com.dvg.minhastarefas.adapters.TarefasPageAdapter
import br.com.dvg.minhastarefas.repository.room.AppDatabase
import com.google.android.material.tabs.TabLayout


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Bind dos elementos TabLayout e ViewPager do Layout
        val tabLayout = findViewById<TabLayout>(R.id.tabTarefas)
        val viewPager = findViewById<ViewPager>(R.id.viewPagerTarefas)

        //Vincula o Adapter com o ViewPager
        viewPager.adapter = TarefasPageAdapter(supportFragmentManager)

        //Vincula o TabLayout com o ViewPager
        tabLayout.setupWithViewPager(viewPager)

        //Desabilitando scroll/swipe horizontal na viewpager
        viewPager.setOnTouchListener { view, motionEvent -> true }

    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance();
    }
}