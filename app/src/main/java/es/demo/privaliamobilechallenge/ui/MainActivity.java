package es.demo.privaliamobilechallenge.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseActivity;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.ListMoviesFragment;
import es.demo.privaliamobilechallenge.ui.listeners.MainListener;

public class MainActivity extends BaseActivity implements MainListener{
    FragmentManager fm;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        pushListFragment();
    }

    public void pushFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if ("DetailMovieFragment".equals(fragment.getClass().getSimpleName())){
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.add(R.id.frame_layout, fragment);
            ft.addToBackStack("ListMoviesFragment");
            ft.commit();
        }else{
            fragmentManager.popBackStackImmediate();
        }
    }

    public void pushListFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.add(R.id.frame_layout, ListMoviesFragment.newInstance());
        ft.addToBackStack("ListMoviesFragment");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount()==0)
            finish();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        pushFragment(fragment);
    }
}
