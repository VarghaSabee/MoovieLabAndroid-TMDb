package com.example.moovielab.ui.fragment;

import com.example.moovielab.ui.fragment.common.MovieListableFragment;
import com.example.moovielab.ui.presenter.InTheatersMoviesListPresenter;
import com.example.moovielab.ui.presenter.base.IPresenter;

/**
 * Fragment representing the movies list in theaters
 */
public class InTheatersMoviesListFragment extends MovieListableFragment {

    @Override
    protected IPresenter createPresenter() {
        return new InTheatersMoviesListPresenter(this);
    }
}
