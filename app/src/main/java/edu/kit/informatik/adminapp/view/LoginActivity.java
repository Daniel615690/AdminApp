package edu.kit.informatik.adminapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.kit.informatik.adminapp.R;
import edu.kit.informatik.adminapp.controller.server.PiAdapter;
import edu.kit.informatik.adminapp.controller.server.ServerAdapter;
import edu.kit.informatik.adminapp.core.Output;
import edu.kit.informatik.adminapp.model.Hostname;
import edu.kit.informatik.adminapp.model.Milliseconds;
import edu.kit.informatik.adminapp.model.Password;
import edu.kit.informatik.adminapp.model.Port;
import edu.kit.informatik.adminapp.model.UserId;
import edu.kit.informatik.adminapp.model.resources.Extras;

/**
 * This class represents an activity for user login.
 * The activity serves as the main activity of the app. The user must log in
 * to use the app.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    private static final String NO_ERROR = "";
    private final Output errorOutput = (String message) -> runOnUiThread(
            () -> ((TextView) findViewById(R.id.mainActivityErrorOutput)).setText(message));

    private ServerAdapter serverAdapter;
    private ExecutorService executor;
    private EditText loginName;
    private EditText password;
    private Button loginButton;
    private final TextWatcher textFieldsWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(final CharSequence charSequence,
                                      final int start, final int count, final int after) {}

        @Override
        public void onTextChanged(final CharSequence charSequence,
                                  final int start, final int before, final int count) {}

        @Override
        public void afterTextChanged(final Editable editable) {
            // If loginName and password are filled, enable the login button
            if (LoginActivity.this.loginName.getText().toString().length() != 0
                    && LoginActivity.this.password.getText().toString().length() != 0) {
                LoginActivity.this.loginButton.setEnabled(true);
            } else {
                LoginActivity.this.loginButton.setEnabled(false);
            }
        }
    };

    /**
     * Loads the view elements of the activity.
     *
     * @param savedInstanceState    contains the status of a previously saved activity,
     *                              {@code null} if the activity has never been left
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginName = findViewById(R.id.activity_login_et_loginName);
        this.password = findViewById(R.id.activity_login_et_password);
        this.loginButton = findViewById(R.id.activity_login_btn_login);

        this.loginName.addTextChangedListener(this.textFieldsWatcher);
        this.password.addTextChangedListener(this.textFieldsWatcher);
        this.loginButton.setEnabled(false);
        this.loginButton.setOnClickListener(this::onLoginClick);
    }

    /**
     * Initializes the content of the options menu of the activity.
     *
     * @param menu  the menu in which the content is placed
     * @return  {code true}
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    /**
     * Starts {@link SettingsActivity} if the settings menu is clicked.
     *
     * @param item  the MenuItem that was clicked
     * @return  {@code true}
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.activity_login_item_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to establish a connection to the server in a new thread.
     * If the connection fails, an appropriate error message is displayed.
     */
    @Override
    protected void onStart() {
        super.onStart();
        createServer(); // If server settings have changed
        this.executor = Executors.newSingleThreadExecutor(); // To process tasks sequentially
        this.executor.submit(this::connect);
    }

    private void createServer() {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        Hostname hostname = new Hostname(preferences.getString(
                getString(R.string.fragment_preferences_hostname_key),
                getString(R.string.fragment_preferences_hostname_default_value)));
        Port port = new Port(Integer.parseInt(preferences.getString(
                getString(R.string.fragment_preferences_port_key),
                getString(R.string.fragment_preferences_port_default_value))));
        Milliseconds timeout = new Milliseconds(Integer.parseInt(preferences.getString(
                getString(R.string.fragment_preferences_timeout_key),
                getString(R.string.fragment_preferences_timeout_default_value))));
        this.serverAdapter = new PiAdapter(hostname, port, timeout);
    }

    private void connect() {
        try {
            if (!this.serverAdapter.isConnected()) {
                this.serverAdapter.connect();
            }
            clearErrorMessage(); // Remove connection error message
        } catch (IOException e) {
            errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    private void clearErrorMessage() {
        errorOutput.output(NO_ERROR);
    }

    private void onLoginClick(final View view) {
        try {
            this.serverAdapter.set(new UserId(this.loginName.getText().toString()));
            this.serverAdapter.set(new Password(this.password.getText().toString()));
            this.executor.submit(this::login);
        } catch (IllegalArgumentException e) {
            errorOutput.output(getString(R.string.ERROR_AUTHENTIFICATION_FAILED));
        }
    }

    private void login() {
        try {
            if (!this.serverAdapter.isConnected()) {
                this.serverAdapter.connect();
            }
            if (this.serverAdapter.isAuthenticated()) {
                clearErrorMessage();    // Remove authentication error message
                startSearchUserActivity();
            } else {
                errorOutput.output(getString(R.string.ERROR_AUTHENTIFICATION_FAILED));
            }
        } catch (IOException e) {
            errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    private void startSearchUserActivity() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        intent.putExtra(Extras.EXTRA_SERVER_ADAPTER, this.serverAdapter);
        runOnUiThread(() -> startActivity(intent));
    }

    /**
     * Closes the connection to the server.
     */
    @Override
    protected void onStop() {
        super.onStop();
        this.executor.submit(this::close);
        this.executor.shutdown();
    }

    private void close() {
        try {
            this.serverAdapter.close();
        } catch (IOException e) {
            // No way to handle the error
        }
    }
}
