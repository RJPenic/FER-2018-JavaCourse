package penic.rafael_josip.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Activity that offers feature to send text based emails.
 *
 * @author Rafael Josip Penić
 */
public class ComposeMailActivity extends AppCompatActivity {

    /**
     * Pattern used to check validity of an email.
     * Source: https://stackoverflow.com/questions/8204680/java-regex-email
     */
    public static final Pattern EMAIL_CHECKER_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        EditText sendToEdit = findViewById(R.id.send_to_edit);
        EditText titleEdit = findViewById(R.id.title_edit);
        EditText messageEdit = findViewById(R.id.message_edit);

        TextView messageLabel = findViewById(R.id.error_label);

        Button backButton = findViewById(R.id.btn_back);
        Button sendButton = findViewById(R.id.btn_send_mail);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sendToEdit.getText().toString().isEmpty() ||
                        titleEdit.getText().toString().isEmpty() ||
                        messageEdit.getText().toString().isEmpty()){
                    messageLabel.setText(R.string.uncomplete_message);
                    return;
                }

                if(!EMAIL_CHECKER_PATTERN.matcher(sendToEdit.getText().toString()).matches()){
                    messageLabel.setText(R.string.email_notvalid);
                    return;
                }

                if(!Character.isUpperCase(titleEdit.getText().toString().charAt(0))){
                    messageLabel.setText(R.string.title_notvalid);
                    return;
                }

                Intent mail_intent = new Intent(Intent.ACTION_SEND);
                mail_intent.setType("message/rfc822");
                mail_intent.putExtra(Intent.EXTRA_EMAIL, new String[]{sendToEdit.getText().toString()});
                mail_intent.putExtra(Intent.EXTRA_SUBJECT, titleEdit.getText().toString());
                mail_intent.putExtra(Intent.EXTRA_TEXT, messageEdit.getText().toString());
                mail_intent.putExtra(Intent.EXTRA_CC, new String[] {"ana@baotic.org", "marcupic@gmail.com"});

                startActivity(Intent.createChooser(mail_intent, "Send email"));
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComposeMailActivity.this, LifecycleActivity.class);

                startActivity(intent);
            }
        });
    }
}
