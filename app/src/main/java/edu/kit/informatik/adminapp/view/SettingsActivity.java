package edu.kit.informatik.adminapp.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.SummaryProvider;
import androidx.preference.PreferenceFragmentCompat;

import edu.kit.informatik.adminapp.R;
import edu.kit.informatik.adminapp.model.Hostname;
import edu.kit.informatik.adminapp.model.Milliseconds;
import edu.kit.informatik.adminapp.model.Port;

/**
 * This class represents an activity for adjusting settings.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Loads the UI.
     *
     * @param savedInstanceState    contains the state of a previously saved activity,
     *                              {@code null} if the activity has never been exited
     */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_settings_fl_settings_container, new SettingsFragment())
                .commit();

        // Enables the home button in the toolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    /**
     * This class loads the fragment that makes up the settings activity.
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {

        /**
         * Loads the preference elements of this fragment.
         *
         * @param savedInstanceState    contains the state of a previously saved fragment,
         *                              {@code null} if the fragment has never been exited
         * @param rootKey               the key to be used as the root of the settings hierarchy
         */
        @Override
        public void onCreatePreferences(final Bundle savedInstanceState,
                                        final String rootKey) {
            setPreferencesFromResource(R.xml.fragment_preferences, rootKey);

            // Input is only used if it is a valid hostname
            EditTextPreference hostnamePreference =
                    findPreference(getString(R.string.fragment_preferences_hostname_key));
            if (hostnamePreference != null) {
                hostnamePreference.setOnPreferenceChangeListener(this::isHostname);
            }

            // Input is only used if it is a valid port
            EditTextPreference portPreference =
                    findPreference(getString(R.string.fragment_preferences_port_key));
            if (portPreference != null) {
                portPreference.setOnPreferenceChangeListener(this::isPort);
            }

            EditTextPreference timeoutPreference =
                    findPreference(getString(R.string.fragment_preferences_timeout_key));
            if (timeoutPreference != null) {
                // Input is only used if it is a valid timeout
                timeoutPreference.setOnPreferenceChangeListener(this::isTimeout);
                // Append the unit ms to the output
                timeoutPreference.setSummaryProvider(
                        (SummaryProvider<EditTextPreference>) preference -> String.format(
                                getString(R.string.activity_settings_timeout_summary),
                                preference.getText()));
            }
        }

        private boolean isTimeout(final Preference preference, final Object newValue) {
            try {
                new Milliseconds(Integer.parseInt((String) newValue));
            } catch (IllegalArgumentException | ClassCastException e) {
                return false;
            }
            return true;
        }

        private boolean isPort(final Preference preference, final Object newValue) {
            try {
                new Port(Integer.parseInt((String) newValue));
            } catch (IllegalArgumentException | ClassCastException e) {
                return false;
            }
            return true;
        }

        private boolean isHostname(final Preference preference, final Object newValue) {
            try {
                new Hostname((String) newValue);
            } catch (IllegalArgumentException | ClassCastException e) {
                return false;
            }
            return true;
        }
    }
}
