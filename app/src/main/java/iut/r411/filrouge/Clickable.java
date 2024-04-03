package iut.r411.filrouge;


import android.content.Context;

/**
 * Interface pour écouter les évènements sur le nom du diplome
 */

public interface Clickable {

    /**
     * se lance quand on clique sur un item d'une listview
     *
     * @param itemIndex l'index de l'item sur le quel on a cliqué
     */
    void onClicItem(int itemIndex);

    Context getContext();

}
