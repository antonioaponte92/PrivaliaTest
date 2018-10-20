package es.demo.privaliamobilechallenge.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.ui.listeners.CategoriesListener;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{
    CategoriesListener listener;
    List<String> list;
    Context context;

    public CategoriesAdapter(CategoriesListener listener, List<String> list, Context context) {
        this.listener = listener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    class  ViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.txtView)
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.txtView)
        void onClick(){
            listener.onClickCat(getAdapterPosition());
        }
    }
}
