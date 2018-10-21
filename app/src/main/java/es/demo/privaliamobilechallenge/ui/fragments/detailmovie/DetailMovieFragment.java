package es.demo.privaliamobilechallenge.ui.fragments.detailmovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;

import butterknife.BindView;
import butterknife.OnClick;
import es.demo.privaliamobilechallenge.BuildConfig;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseFragment;
import es.demo.privaliamobilechallenge.commons.Consts;
import es.demo.privaliamobilechallenge.commons.Utils;
import es.demo.privaliamobilechallenge.data.models.Movie;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.ListMoviesFragment;

public class DetailMovieFragment extends BaseFragment{
    @BindView(R.id.tv_title)        TextView tv_title;
    @BindView(R.id.tv_overview)     JustifiedTextView tv_overview;
    @BindView(R.id.tv_year)         TextView tv_year;
    @BindView(R.id.iv_poster)       ImageView iv_poster;
    @BindView(R.id.iv_backdrop)     ImageView iv_back;

    public static DetailMovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(Consts.Keys.DATA,movie);
        DetailMovieFragment fragment = new DetailMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            Movie movie = (Movie) getArguments().get(Consts.Keys.DATA);
            tv_title.setText(movie.getOriginalTitle());
            tv_year.setText(Utils.getYear(movie.getReleaseDate()));
            tv_overview.setText(movie.getOverview());
            Glide.with(this).load(BuildConfig.PHOTO_URL+ movie.getBackdropPath()).into(iv_back);
            Glide.with(this).load(BuildConfig.PHOTO_URL+ movie.getPosterPath()).into(iv_poster);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_detail;
    }

    @OnClick(R.id.iv_back)
    void goBack(){
        listener.changeFragment(ListMoviesFragment.newInstance());
    }
}
