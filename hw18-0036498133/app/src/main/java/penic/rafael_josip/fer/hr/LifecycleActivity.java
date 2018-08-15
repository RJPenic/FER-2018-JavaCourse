package penic.rafael_josip.fer.hr;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/**
 * Activity representing a home screen offering basic options of the application and ability to divide numbers
 *
 * @author Rafael Josip Penić
 */
public class LifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        EditText inputFirst = findViewById(R.id.input_first);
        EditText inputSecond = findViewById(R.id.input_second);
        TextView labelResult = findViewById(R.id.output_result);
        Button btnCalculate = findViewById(R.id.btn_calculate);

        Button langButton = findViewById(R.id.btn_en);

        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale current = getResources().getConfiguration().locale;

                Locale locale;
                if(current.toString().equals("en")){
                    locale = new Locale("hr");
                } else {
                    locale = new Locale("en");
                }

                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, null);

                Intent intent = new Intent(LifecycleActivity.this, LifecycleActivity.class);

                startActivity(intent);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = inputFirst.getText().toString();
                String second = inputSecond.getText().toString();

                int firstNumber = 0;
                int secondNumber = 0;

                try {
                    firstNumber = Integer.parseInt(first);
                } catch (NumberFormatException ex){
                    //ignorable
                }

                try {
                    secondNumber = Integer.parseInt(second);
                } catch (NumberFormatException ex){
                    //ignorable
                }

                if(secondNumber != 0){
                    labelResult.setText(String.valueOf(firstNumber / secondNumber));
                } else {
                    labelResult.setText(R.string.nedozv_op);
                }
            }
        });

        Button btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this, ShowActivity.class);

                Bundle extras = new Bundle();
                extras.putString("rezultat", labelResult.getText().toString());

                intent.putExtras(extras);

                startActivity(intent);
            }

        });

        Button btnMail = findViewById(R.id.btn_compose_mail);

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this, ComposeMailActivity.class);
                startActivity(intent);
            }
        });

        Log.d("Lifecycle", "Pozvao oncreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "Pozvao onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "Pozvao onresume");
    }
}
