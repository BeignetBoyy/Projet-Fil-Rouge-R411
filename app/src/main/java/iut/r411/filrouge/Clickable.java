package iut.r411.filrouge;


import android.content.Context;

/**
 * Interface pour écouter les évènements sur le nom du diplome
 */

    public interface Clickable {
        void onClicItem(int itemIndex);
        Context getContext();

        void onRatingBarChange(int itemIndex, float value);//TODO REMOVE
}
