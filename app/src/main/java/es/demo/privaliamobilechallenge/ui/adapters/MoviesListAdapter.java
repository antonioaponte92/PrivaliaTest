package es.demo.privaliamobilechallenge.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.demo.privaliamobilechallenge.BuildConfig;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.data.models.Movie;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder>{
    private Context context;
    private List<Movie> list;
    MoviesRecyclerListener listener;

    public MoviesListAdapter(Context context, MoviesRecyclerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Movie> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<Movie> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String year = list.get(i).getReleaseDate().split("-")[0];
        Glide.with(context).load(BuildConfig.PHOTO_URL+list.get(i).getBackdropPath()).into(holder.iv_poster);
        holder.tv_name.setText(context.getString(R.string.title_year)
                .replace("REPLACE_TITLE",list.get(i).getOriginalTitle())
                .replace("REPLACE_YEAR",year));
        holder.tv_overview.setText(list.get(i).getOverview());
        if (i == list.size()-1){
            listener.loadMore();
        }
    }
    @Override
    public int getItemCount() {
        if (list==null)
            return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_poster)
        ImageView iv_poster;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_overview)
        JustifiedTextView tv_overview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.card_view)
        public void showMore(){
            listener.onItemClick(list.get(getAdapterPosition()));
        }
    }

    public interface MoviesRecyclerListener{
        void onItemClick(Movie movie);
        void loadMore();
    }
}
