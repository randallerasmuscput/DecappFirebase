package za.ac.cput.decappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 2016/05/12.
 */
public class UpdateActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Button btnUpdate =(Button)findViewById(R.id.btnUpdate);
        Button btnBack = (Button)findViewById(R.id.btnBack);

        //this also works like a charm
        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnUpdate:
                Intent signUpIntent = new Intent(UpdateActivity.this,MainActivity.class);
                //here the service goes
                startActivity(signUpIntent);

                case R.id.btnBack:
                Intent decappIntent = new Intent(UpdateActivity.this,DecappActivity.class);
                startActivity(decappIntent);
        }

    }
}
