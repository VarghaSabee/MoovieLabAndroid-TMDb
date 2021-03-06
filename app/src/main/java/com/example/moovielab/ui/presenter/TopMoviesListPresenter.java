package com.example.moovielab.ui.presenter;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import com.example.moovielab.data.exception.FailedGettingDataException;
import com.example.moovielab.data.repository.base.ICloudMovieRepository;
import com.example.moovielab.data.repository.base.MovieRepositoryFactory;
import com.example.moovielab.model.Movie;
import com.example.moovielab.ui.contract.ILoadDataView;
import com.example.moovielab.ui.fragment.common.MovieListableFragment;
import com.example.moovielab.ui.presenter.base.ListablePresenter;

/**
 * Top movies presenter
 */
public class TopMoviesListPresenter extends ListablePresenter<List<Movie>> {

    public TopMoviesListPresenter(
            ILoadDataView<List<Movie>> view) {
        super(view);
    }

    @Override
    public void execute() {
        new LoadDataTask().execute();
    }

    @Override
    public void getMoreData(int page) {
        new LoadMorePagesTask().execute(page);
    }

    @Override
    public void refresh() {
        new LoadDataTask().execute();
    }

    /**
     * Download top movie list in a worker thread using an AsyncTask
     * From cloud repo
     */
    private class LoadDataTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: Getting movies from the api");
            ICloudMovieRepository repo = MovieRepositoryFactory.getCloudRepository();

            try {
                return repo.getTopMovies(1);
            } catch (FailedGettingDataException e) {
                Log.d(TAG, "Failed getting data! Error: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);

            if(list != null)
                view.setData(list);
            else
                view.showNoConnection();
        }
    }

    /**
     * Download specific page top movie list and adds to the list in a worker thread using an AsyncTask
     * From cloud repo
     */
    private class LoadMorePagesTask extends AsyncTask<Integer, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Integer... params) {
            Log.d(TAG, "doInBackground: Getting movies from the api");
            ICloudMovieRepository repo = MovieRepositoryFactory.getCloudRepository();

            try {
                return repo.getTopMovies(params[0]);
            } catch (FailedGettingDataException e) {
                Log.d(TAG, "Failed getting data! Error: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);

            ((MovieListableFragment)view).addMoreData(list);
        }
    }
}
