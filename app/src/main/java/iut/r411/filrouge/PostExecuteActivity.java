package iut.r411.filrouge;

import java.util.List;

public interface PostExecuteActivity<T> {
        void onPostExecute(List<T> itemList);
        void runOnUiThread(Runnable runable);
}
