package com.example.moovielab.ui.fragment;

import com.example.moovielab.ui.fragment.common.MovieListableFragment;
import com.example.moovielab.ui.presenter.TopMoviesListPresenter;
import com.example.moovielab.ui.presenter.base.IPresenter;

/**
 * Top movies fragment
 */
public class TopMoviesListFragment extends MovieListableFragment {

    @Override
    protected IPresenter createPresenter() {
        return new TopMoviesListPresenter(this);
    }
}
