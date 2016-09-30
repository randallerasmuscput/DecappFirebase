package za.ac.cput.decappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by User on 2016/06/11.
 */
public class RemoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
    }

    public void onBackClick(View v)
    {
        if(v.getId()==R.id.btnBack)
        {
            Intent intent = new Intent(this,DecappActivity.class);
            startActivity(intent);
        }
    }
}
