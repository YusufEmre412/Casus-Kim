package com.stark.myapplication;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {
    private TextView themeTextView;
    private boolean isDarkTheme;
    private Switch deliRoleSwitch;
    private boolean isDeliRoleEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        themeTextView = findViewById(R.id.themeTextView);
        deliRoleSwitch = findViewById(R.id.deliRoleSwitch); // Switch'i alıyoruz

        SharedPreferences preferences = getSharedPreferences("themePrefs", MODE_PRIVATE);
        isDarkTheme = preferences.getBoolean("isDarkTheme", false);
        isDeliRoleEnabled = preferences.getBoolean("isDeliRoleEnabled", false); // Deli rolü tercihi

        updateThemeText();
        deliRoleSwitch.setChecked(isDeliRoleEnabled); // Switch'in durumunu ayarlıyoruz

        themeTextView.setOnClickListener(this::showThemePopup);

        // Deli rolü için switch dinleyici
        deliRoleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDeliRoleEnabled = isChecked;
            saveSettings();
        });
    }

    private void showThemePopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenu().add("Aydınlık");
        popupMenu.getMenu().add("Karanlık");

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            // Seçilen tema ayarını güncelle
            if (menuItem.getTitle().equals("Aydınlık")) {
                isDarkTheme = false;
            } else if (menuItem.getTitle().equals("Karanlık")) {
                isDarkTheme = true;
            }
            applyTheme();  // Yeni tema ayarını uygula
            return true;
        });
        popupMenu.show();
    }

    private void applyTheme() {
        // Yeni tema ayarını uygulama ve kaydetme
        AppCompatDelegate.setDefaultNightMode(isDarkTheme ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        saveSettings();
    }

    private void saveSettings() {
        // Tema ve deli rolü tercihini kaydediyoruz
        SharedPreferences.Editor editor = getSharedPreferences("themePrefs", MODE_PRIVATE).edit();
        editor.putBoolean("isDarkTheme", isDarkTheme);
        editor.putBoolean("isDeliRoleEnabled", isDeliRoleEnabled); // Deli rolü tercihini kaydediyoruz
        editor.apply();
    }

    private void updateThemeText() {
        // Seçilen temaya göre TextView içeriğini güncelle
        themeTextView.setText("Tema: " + (isDarkTheme ? "Karanlık" : "Aydınlık"));
    }
}