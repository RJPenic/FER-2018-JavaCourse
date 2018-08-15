package penic.rafael_josip.fer.hr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that shows one edit text box which receives result from the main activity
 *
 * @author Rafael Josip Penić
 */
public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String result = getIntent().getExtras().getString("rezultat");

        EditText resBox = findViewById(R.id.resut_box);

        resBox.setText(result);

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
}
