package com.example.moovielab.ui.presenter;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import com.example.moovielab.Utils;
import com.example.moovielab.data.exception.FailedGettingDataException;
import com.example.moovielab.data.provider.MoviesContract;
import com.example.moovielab.data.repository.base.ICloudMovieRepository;
import com.example.moovielab.data.repository.base.ILocalMovieRepository;
import com.example.moovielab.data.repository.base.MovieRepositoryFactory;
import com.example.moovielab.model.Movie;
import com.example.moovielab.ui.contract.ILoadDataView;
import com.example.moovielab.ui.presenter.base.Presenter;

/**
 * Splash presenter, contains the logic for download the data for the first time and put it
 * on the local repo
 */
public class SplashPresenter extends Presenter<Void> {

    private static final int STATUS_OK = 0;
    private static final int STATUS_ERROR = 1;
    private static final int STATUS_NO_CONNECTION = 2;

    public SplashPresenter(ILoadDataView<Void> view) {
        super(view);
    }

    @Override
    public void execute() {
        new LoadDataTask().execute();
    }

    /**
     * Download data from the cloud if the local repo (content provider) is empty
     */
    private class LoadDataTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);

            switch (status) {
                case STATUS_OK:
                    view.setData(null);
                    break;
                case STATUS_NO_CONNECTION:
                    view.showNoConnection();
                    break;
                case STATUS_ERROR:
                default:
                    view.showError("Ooops! Error!");
            }
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ILocalMovieRepository localRepo = MovieRepositoryFactory
                    .getLocalRepository(view.getViewContext());

            // TODO: 13/01/2016 check internet connection
            try {
                //Check if local repo has movies, if not download!
                if (!localRepo.hasMovies()) {

                    if (!Utils.isConnected(view.getViewContext())) {
                        return STATUS_NO_CONNECTION;
                    }

                    ICloudMovieRepository cloudRepo = MovieRepositoryFactory.getCloudRepository();
                    List<Movie> now = cloudRepo.getTheatersMovies(1);
                    List<Movie> soon = cloudRepo.getSoonMovies(1);

                    localRepo.insertMovies(now, MoviesContract.MovieEntry.TYPE_NOW);
                    localRepo.insertMovies(soon, MoviesContract.MovieEntry.TYPE_SOON);

                    Log.d(TAG, "Inserted movies list in the local repo!");
                } else {
                    Log.d(TAG, "There are already movies in the repo, no downloads!");
                }
                return STATUS_OK;
            } catch (FailedGettingDataException e) {
                Log.d(TAG, "Failed getting data! Error: " + e.getMessage());
                return STATUS_ERROR;
            }
        }
    }
}
