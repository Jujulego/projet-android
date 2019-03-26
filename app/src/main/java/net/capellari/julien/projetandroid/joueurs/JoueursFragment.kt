package net.capellari.julien.projetandroid.joueurs

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_joueur.view.*
import net.capellari.julien.fragments.ListFragment
import net.capellari.julien.projetandroid.DataViewModel
import net.capellari.julien.projetandroid.R
import net.capellari.julien.projetandroid.db.Joueur
import net.capellari.julien.utils.inflate

class JoueursFragment : ListFragment() {
    // Attributs
    private val adapter = JoueursAdapter()
    private lateinit var data: DataViewModel

    // Events
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // view model !
        data = ViewModelProviders.of(requireActivity())[DataViewModel::class.java]
        data.allJoueurs().observe(this, Observer {
            adapter.joueurs = it
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onRecyclerViewCreated(view: RecyclerView) {
        view.adapter = adapter
        view.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_joueurs, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_ajout_joueur -> { AjoutJoueurDialog().show(childFragmentManager, "dialog"); true }
            else -> false
        }
    }

    // Classes
    class JoueursAdapter : RecyclerView.Adapter<JoueurHolder>() {
        // Attributs
        var joueurs = arrayOf<Joueur>()

        // Méthodes
        override fun getItemCount(): Int = joueurs.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoueurHolder {
            return JoueurHolder(parent.inflate(R.layout.item_joueur))
        }

        override fun onBindViewHolder(holder: JoueurHolder, position: Int) {
            holder.bind(joueurs[position])
        }
    }

    class JoueurHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // Attributs
        var joueur: Joueur? = null

        // Méthodes
        fun bind(joueur: Joueur) {
            this.joueur = joueur

            // vues
            view.nom.text = joueur.nom
        }
    }
}