package es.demo.privaliamobilechallenge.commons;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import es.demo.privaliamobilechallenge.ui.listeners.MainListener;

public abstract class BaseFragment extends Fragment{
    protected MainListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MainListener) context;
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayout();

    protected void showSnackBar(int intRes){
        showSnackBar(getString(intRes));
    }
    protected void showSnackBar(String text){
        if (getView()!=null)
            Snackbar.make(getView(),text,Snackbar.LENGTH_SHORT).show();
    }
}
