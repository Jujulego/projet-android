package net.capellari.julien.projetandroid.db

import androidx.lifecycle.LiveData
import androidx.room.*
import net.capellari.julien.utils.DiffItem

@Entity
data class Joueur(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var nom: String,
    var prenom: String
): DiffItem<Joueur> {
    // Méthodes
    override fun isSameItem(other: Joueur): Boolean {
        return id == other.id
    }

    override fun isSameContent(other: Joueur): Boolean {
        return nom == other.nom && prenom == other.prenom
    }

    // Dao
    @Dao
    interface JoueurDao {
        // Accès
        @Query("select * from Joueur order by nom, prenom")
        fun all(): LiveData<Array<Joueur>>

        @Query("select * from Joueur where id = :id")
        fun getById(id: Int): LiveData<Joueur>

        // Modifications
        @Insert fun insert(joueur: Joueur)
        @Update fun update(joueur: Joueur)
        @Delete fun delete(joueur: Joueur)
    }
}