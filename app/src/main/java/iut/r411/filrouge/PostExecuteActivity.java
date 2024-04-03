package iut.r411.filrouge;

import java.util.List;

public interface PostExecuteActivity<T> {

        /**
         * Se lance quand la requete http à reussi
         *
         * @param itemList liste des items recupérer dans la requete http
         */
        void onPostExecute(List<T> itemList);
        void runOnUiThread(Runnable runable);
}
