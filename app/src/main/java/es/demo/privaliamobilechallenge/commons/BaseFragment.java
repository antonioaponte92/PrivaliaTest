package es.demo.privaliamobilechallenge.commons;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    protected void showToast(int intRes){
        showToast(getString(intRes));
    }
    protected void showToast(String text){
        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
    }
}
