package es.demo.privaliamobilechallenge.ui.detailmovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.demo.privaliamobilechallenge.BuildConfig;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.Consts;
import es.demo.privaliamobilechallenge.commons.Utils;
import es.demo.privaliamobilechallenge.data.models.Movie;

public class DetailMovieFragment extends Fragment{
    @BindView(R.id.tv_title)        TextView tv_title;
    @BindView(R.id.tv_overview)     TextView tv_overview;
    @BindView(R.id.tv_year)         TextView tv_year;
    @BindView(R.id.iv_poster)       ImageView iv_poster;

    public static DetailMovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(Consts.Keys.DATA,movie);
        DetailMovieFragment fragment = new DetailMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        if (getArguments()!=null){
            Movie movie = (Movie) getArguments().get(Consts.Keys.DATA);
            tv_title.setText(movie.getOriginalTitle());
            tv_year.setText(Utils.getYear(movie.getReleaseDate()));
            tv_overview.setText(movie.getOverview());
            Glide.with(this).load(BuildConfig.PHOTO_URL+ movie.getBackdropPath()).into(iv_poster);
        }
        return view;
    }
}
