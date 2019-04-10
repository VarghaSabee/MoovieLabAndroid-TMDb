package com.example.moovielab.ui.fragment;

import com.example.moovielab.ui.fragment.common.MovieListableFragment;
import com.example.moovielab.ui.presenter.SoonMoviesListPresenter;
import com.example.moovielab.ui.presenter.base.IPresenter;

/**
 * Soon movies fragment
 */
public class SoonMoviesListFragment extends MovieListableFragment {

    @Override
    protected IPresenter createPresenter() {
        return new SoonMoviesListPresenter(this);
    }
}
