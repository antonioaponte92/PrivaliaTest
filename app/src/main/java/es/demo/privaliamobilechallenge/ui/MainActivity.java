package es.demo.privaliamobilechallenge.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseActivity;
import es.demo.privaliamobilechallenge.ui.listmovies.ListMoviesFragment;

public class MainActivity extends BaseActivity {
    FragmentManager fm;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        showFragment(ListMoviesFragment.newInstance());
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout,fragment);
        ft.commit();
    }

}
