package com.ngg.cloudefirestoreexemplos.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ngg.cloudefirestoreexemplos.fragments.ContatosFragment
import com.ngg.cloudefirestoreexemplos.fragments.ConversasFragment
import com.ngg.cloudefirestoreexemplos.fragments.PostagensFragment

class ViewPagerAdapter(
    private val abas: List<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
):  FragmentStateAdapter( fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return abas.size
    }

    override fun createFragment(position: Int): Fragment {
        when( position ) {
            1 -> return ContatosFragment()
            2 -> return PostagensFragment()
        }
        return ConversasFragment()
    }
}