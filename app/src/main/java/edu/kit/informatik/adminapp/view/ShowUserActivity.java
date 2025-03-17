package edu.kit.informatik.adminapp.view;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.kit.informatik.adminapp.R;
import edu.kit.informatik.adminapp.controller.HashFunction;
import edu.kit.informatik.adminapp.controller.SHA256;
import edu.kit.informatik.adminapp.controller.server.ServerAdapter;
import edu.kit.informatik.adminapp.core.Output;
import edu.kit.informatik.adminapp.model.Attribute;
import edu.kit.informatik.adminapp.model.Token;
import edu.kit.informatik.adminapp.model.TokenId;
import edu.kit.informatik.adminapp.model.User;
import edu.kit.informatik.adminapp.model.resources.Extras;

/**
 * This class represents an activity for displaying a user and adding or removing
 * NFC tokens associated with them.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class ShowUserActivity extends AppCompatActivity {
    // ATTRIBUTE_KEY_USER_NAME, BASE are not used by PiAdapter
    private static final String ATTRIBUTE_KEY_UID = "?";
    private static final String BASE = "?";
    // Defines filters and technologies for NFC tag detection
    private static final IntentFilter[] INTENT_FILTERS = new IntentFilter[]{
            new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)};
    private static final String[][] TECH_LIST = new String[][]{
            new String[]{MifareClassic.class.getName()}};
    // Hash function used to encrypt the NFC token ID
    private static final HashFunction HASH_FUNCTION = new SHA256();
    private final Output errorOutput = (String message) -> runOnUiThread(
            () -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());

    private TextView activity_showUser_tv_name;
    private TextView activity_showUser_tv_tokens;
    private Button deleteTokenButton;

    private ExecutorService executor;
    private ServerAdapter serverAdapter;
    private User user;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent; // Intent triggered when reading an NFC token

    /**
     * Loads the view elements of the activity.
     *
     * @param savedInstanceState    contains the state of a previously saved activity,
     *                              {@code null} if the activity has never been exited
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        this.activity_showUser_tv_name = findViewById(R.id.activity_show_user_et_name);
        this.activity_showUser_tv_tokens = findViewById(R.id.activity_show_user_et_tokens);
        this.deleteTokenButton = findViewById(R.id.activity_show_user_btn_delete);
        this.serverAdapter = getIntent().getParcelableExtra(Extras.EXTRA_SERVER_ADAPTER);
        this.user = getIntent().getParcelableExtra(Extras.EXTRA_USER);
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(
                this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_MUTABLE);

        this.deleteTokenButton.setOnClickListener(
                (View view) -> executor.submit(this::onDeleteTokenClick));

        // Enables the home button in the toolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        updateView();
    }

    /**
     * Ends this activity if the home button in the toolbar is clicked.
     *
     * @param item  the MenuItem that was clicked
     * @return      {@code true}
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDeleteTokenClick() {
        this.executor.submit(this::deleteAllTokens);
    }

    private void deleteAllTokens() {
        try {
            if (!this.serverAdapter.isConnected()) {
                this.serverAdapter.connect();
            }
            serverAdapter.removeAllTokens(user);
            this.user.removeAllTokens();
            runOnUiThread(this::updateView);
        } catch (IOException e) {
            errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    private void updateView() {
        this.activity_showUser_tv_name.setText(this.user.getName().toString());
        this.activity_showUser_tv_tokens.setText(String.valueOf(this.user.getTokens().size()));
    }

    /**
     * Checks if NFC is enabled and attempts to establish a connection to the server
     * in a new thread.
     * If NFC is not enabled or no connection to the server is possible, an appropriate
     * error message is displayed.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Check if NFC is enabled
        if (this.nfcAdapter == null || !this.nfcAdapter.isEnabled()) {
            this.errorOutput.output(getString(R.string.ERROR_NFC_DISABLED));
        }

        this.executor = Executors.newSingleThreadExecutor(); // To process tasks sequentially
        this.executor.submit(this::connect);
    }

    private void connect() {
        try {
            if (!this.serverAdapter.isConnected()) {
                this.serverAdapter.connect();
            }
        } catch (IOException e) {
            errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    /**
     * Enables foreground dispatch for NFC tokens of type Mifare Classic.
     * This ensures that this activity is notified of a newly scanned NFC token
     * before all other activities via the method {@link #onNewIntent(Intent)}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.nfcAdapter.enableForegroundDispatch(
                this, this.pendingIntent, INTENT_FILTERS, TECH_LIST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.nfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * Adds the hashed ID of a scanned NFC token to the displayed user.
     * If no connection to the server exists or the token could not be added
     * to the user, an appropriate error message is displayed.
     *
     * @param intent    the new intent started for this activity
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Token token = createHashedToken(intent);
        if (token != null && !this.user.getTokens().contains(token)) {
            this.executor.submit(() -> addToken(token));
        }
    }

    private void addToken(Token token) {
        try {
            if (!this.serverAdapter.isConnected()) {
                this.serverAdapter.connect();
            }
            this.serverAdapter.addToken(this.user, token);

            // Since only tokens not already stored on the server are added,
            // we must check whether the addition was successful
            Collection<Token> newTokens = new ArrayList<>(this.serverAdapter.search(
                    BASE, new Attribute(ATTRIBUTE_KEY_UID, this.user.getId().toString())))
                    .get(0).getTokens();
            if (newTokens.contains(token)) {
                // Adding was successful
                this.user.add(token);
                runOnUiThread(this::updateView);
            } else {
                errorOutput.output(getString(R.string.ERROR_ADD_FAILED));
            }
        } catch (IOException e) {
            errorOutput.output(getString(R.string.ERROR_CONNECT_FAILED));
        }
    }

    private Token createHashedToken(Intent intent) {
        final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        final byte[] bytes = tag.getId();
        final String bytesToHex = bytesToHex(bytes);

        try {
            String hashedHex = HASH_FUNCTION.hash(bytesToHex);
            return new Token(new TokenId(hashedHex));
        } catch (final NoSuchAlgorithmException e) {
            errorOutput.output(getString(R.string.ERROR_HASH_FAILURE));
            return null;
        }
    }

    /**
     * Converts a byte array into a hexadecimal string representation.
     *
     * Note: This method is adapted from
     * https://stackoverflow.com/questions/9655181/
     * how-to-convert-a-byte-array-to-a-hex-string-in-java
     *
     * @param bytes the byte array
     * @return  the hexadecimal representation of {@code bytes}
     */
    private static String bytesToHex(final byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        final char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            final int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
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