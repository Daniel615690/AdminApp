package edu.kit.informatik.adminapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.kit.informatik.adminapp.R;
import edu.kit.informatik.adminapp.controller.server.ServerAdapter;
import edu.kit.informatik.adminapp.core.Output;
import edu.kit.informatik.adminapp.model.Attribute;
import edu.kit.informatik.adminapp.model.User;
import edu.kit.informatik.adminapp.model.resources.Extras;

/**
 * This class represents an activity that allows searching for users on a server.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class SearchUserActivity extends AppCompatActivity {
    // ATTRIBUTE_KEY_USER_NAME, BASE are not used by PiAdapter
    private static final String ATTRIBUTE_KEY_USER_NAME = "sn";
    private static final String BASE = "";
    private final Output errorOutput = (String message) -> runOnUiThread(
            () -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());

    private ServerAdapter serverAdapter;
    private ExecutorService executor;
    private EditText searchName;
    private Button searchButton;
    private ListView userList;
    private ArrayAdapter<User> userListAdapter;

    /**
     * Loads the view elements of the activity and attempts to establish a connection to
     * the server in a new thread. If the connection fails, an appropriate error message
     * is displayed.
     *
     * @param savedInstanceState    contains the state of a previously saved activity,
     *                              {@code null} if the activity has never been exited
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        this.executor = Executors.newSingleThreadExecutor(); // To process tasks sequentially
        this.serverAdapter = getIntent().getParcelableExtra(Extras.EXTRA_SERVER_ADAPTER);
        this.searchName = findViewById(R.id.activity_search_user_et_searchName);
        this.searchButton = findViewById(R.id.activity_search_user_btn_search);
        this.userList = findViewById(R.id.activity_search_user_lv_users);

        this.searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int start,
                                          final int count, final int after) {}

            @Override
            public void onTextChanged(final CharSequence charSequence, final int start,
                                      final int before, final int count) {}

            @Override
            public void afterTextChanged(final Editable editable) {
                if (searchName.getText().length() == 0 && searchButton.isEnabled()) {
                    disableSearchButton();
                } else if (!searchButton.isEnabled()) {
                    enableSearchButton();
                }
            }
        });
        disableSearchButton();
        this.searchButton.setOnClickListener((View view) -> onSearchButtonClick());

        this.userListAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1);
        this.userList.setAdapter(this.userListAdapter);
        this.userList.setOnItemClickListener(this::onItemClick);

        // connect() cannot be called in onStart() because connect() must also be called in
        // onRestart(), which could result in connect() being called twice
        this.executor.submit(this::connect);
    }

    private void onItemClick(final AdapterView<?> adapterView,
                             final View view, final int position, final long id) {
        Intent intent = new Intent(this, ShowUserActivity.class);
        intent.putExtra(Extras.EXTRA_SERVER_ADAPTER, this.serverAdapter)
                .putExtra(Extras.EXTRA_USER, this.userListAdapter.getItem(position));
        startActivity(intent);
    }

    private void enableSearchButton() {
        this.searchButton.setEnabled(true);
    }

    private void disableSearchButton() {
        this.searchButton.setEnabled(false);
    }

    private void onSearchButtonClick() {
        this.executor.submit(this::searchForUsers);
    }

    private void searchForUsers() {
        runOnUiThread(() -> clearUserList());    // Clear previous search results
        try {
            if (!this.serverAdapter.isConnected()) {
                this.serverAdapter.connect();
            }
            Attribute searchAttribute = new Attribute(
                    ATTRIBUTE_KEY_USER_NAME, this.searchName.getText().toString());
            Collection<User> users = this.serverAdapter.search(BASE, searchAttribute);
            if (users != null && users.size() > 0) {
                runOnUiThread(() -> this.userListAdapter.addAll(users));
            } else {
                this.errorOutput.output(getString(R.string.ERROR_NO_ENTRY_FOUND));
            }
        } catch (IOException e) {
            this.errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    private void clearUserList() {
        this.userListAdapter.clear();
    }

    private void connect() {
        try {
            if (!this.serverAdapter.isConnected()) {
                serverAdapter.connect();
            }
        } catch (IOException e) {
            errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    /**
     * Reloads the last search request.
     * If no connection to the server is possible, an appropriate error message
     * is displayed.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        this.executor = Executors.newSingleThreadExecutor(); // To process tasks sequentially
        onSearchButtonClick();
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
