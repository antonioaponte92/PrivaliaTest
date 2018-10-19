package es.demo.privaliamobilechallenge.commons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        onCreateView(savedInstanceState);

    }

    public abstract int getLayout();
    public abstract void onCreateView(@Nullable Bundle savedInstanceState);

    protected void showToast(int intRes){
        showToast(getString(intRes));
    }
    protected void showToast(String text){
        Toast.makeText(BaseActivity.this,text,Toast.LENGTH_SHORT).show();
    }
}
