package es.demo.privaliamobilechallenge.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseActivity;
import es.demo.privaliamobilechallenge.ui.listeners.MainListener;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.ListMoviesFragment;

public class MainActivity extends BaseActivity implements MainListener{
    FragmentManager fm;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        pushFragment(ListMoviesFragment.newInstance());
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout,fragment);
        ft.commit();
    }

    public void pushFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void changeFragment(Fragment fragment) {
        pushFragment(fragment);
    }
}
