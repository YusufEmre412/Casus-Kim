package com.stark.myapplication;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ArrayList<String> oyuncuIsimleri = new ArrayList<>();
    private HashMap<String, String> oyuncuKelimeMapi = new HashMap<>();
    // Kelime eklenecek:
    private List<String> kelimeler = List.of("Yatak", "Otopark", "Tenis", "Sandalye", "Telefon", "Masa", "Marvel", "ABD", "Siyaset", "Mezarlık", "Poşet", "Leğen",
            "Otobüs Durağı", "Kredi Kartı", "Valilik", "Pasaport", "Coğrafya", "Hastane", "Fare", "Harry Potter", "Deniz", "Kola", "Esaretin Bedeli", "Morgan Freeman",
            "Hapishane", "Çiftlik", "Düğün", "Alt Yazı", "Tünel", "Masa Oyunları", "Gemi", "İlkokul", "Futbol", "Sinema", "Tiyatro", "Restoran", "Kalem", "Defter",
            "Kitap", "Yazılım", "Uçak", "Araba", "Banka", "Müzik", "Çikolata", "Hamburger", "Taksi", "Metro", "Bilgisayar", "Otel", "Kütüphane", "İnternet",
            "Dondurma", "Park", "Balık", "Spor", "Haber", "Geometri", "Akademi", "Müze", "Hastalık", "Gitar", "Öğretmen", "Öğrenci", "Simit", "Sahil", "Fabrika",
             "Dağ", "Orman", "Bilim", "Market", "Siyasetçi", "Mimarlık", "Mutfak", "Dizi", "Film", "Anı", "Felsefe", "Arkeoloji", "Edebiyat", "Botanik", "Turist", "Doktor",
            "Gazete", "Bisiklet", "Kütüphane", "Robot", "Sanat", "Mimar", "Tatil", "Plaj", "Deniz", "Kayak", "Yarış", "Zooloji", "Resim", "Heykel", "Heykeltraş", "Bira",
            "Çay", "Kahve", "Otobüs", "Pilates", "Yoga", "Sanat Galerisi", "Mücevher", "Keman", "Piyano", "Orkestra", "Şehir", "Köy", "Kasaba", "Galaksi", "Köprü", "Balina",
            "Usta", "Dalgıç", "Harita", "Meteoroloji", "Radyoloji", "Havacılık", "Paraşüt", "Patika", "Gözlük", "Saat", "Çorap", "Kumbara", "Kafe", "Buzdolabı", "Bakkal", "Fizik", "Kimya",
            "Kitaplık", "Kumsal", "Dağ Evi", "Fotoğraf Makinesi", "Karnaval", "Halka", "Kumbara", "Fenerbahçe", "Kalemlik", "Atatürk", "Sirk", "Saksı", "Ayakkabı Kutusu", "Cep Aynası", "Uzay", "Paraşüt", "Sanat Galerisi",
            "Trafik Işığı", "Güneş Gözlüğü", "Asansör", "Kavun", "Araba Lastiği", "Sözlük", "Takvim", "Çikolata", "Kuş Yuvası", "Fuar", "Akvaryum", "Piyano", "Çaydanlık", "Termos", "Yatak Örtüsü",
            "Şemsiye", "Bahçe Kapısı", "El Feneri", "Kitap Ayracı", "Dünya Haritası", "Kavanoz", "Dalış Maskesi", "Resim Çerçevesi", "Renk Paleti", "Termometre",
            "Pizza", "Kum Saati", "Mikroskop", "Teleskop", "Balon", "Buzdolabı", "Televizyon Kumandası", "Bisiklet", "Kulaklık", "Çalar Saat",
            "Lamba", "Anahtar", "Posta Kutusu", "Cüzdan", "Su Şişesi", "Keman", "Plak", "At", "Duvar Saati", "Pencere","Masa Saati", "Kumbara","Tükenmez Kalem", "Defter", "Klavye",
            "Şarj Aleti", "Kablo", "Avize","Çamaşır Makinesi", "Kitap Kapağı", "Baharatlık", "Tablo", "Kilit", "Kalemtraş", "Düğme", "Terlik", "İlkyardım Çantası", "Kumanda"
    );
    private EditText oyuncuAdiGirisi;
    private Button oyuncuEkleButonu, oyunuBaslatButonu;
    private ListView oyuncuListesiView;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            settingsButton.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN); // Beyaz renk (Karanlık tema)
        } else {
            settingsButton.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN); // Siyah renk (Aydınlık tema)
        }


        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });



        oyuncuAdiGirisi = findViewById(R.id.playerNameInput);
        oyuncuEkleButonu = findViewById(R.id.addPlayerButton);
        oyunuBaslatButonu = findViewById(R.id.startGameButton);
        oyuncuListesiView = findViewById(R.id.playerList);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, oyuncuIsimleri);
        oyuncuListesiView.setAdapter(adapter);

        oyuncuEkleButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oyuncuAdi = oyuncuAdiGirisi.getText().toString().trim();
                if (!oyuncuAdi.isEmpty()) {
                    oyuncuIsimleri.add(oyuncuAdi);
                    adapter.notifyDataSetChanged();
                    oyuncuAdiGirisi.setText("");

                    if (oyuncuIsimleri.size() >= 3) {
                        oyunuBaslatButonu.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lütfen bir oyuncu ismi girin.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Oyuncu kaldırma işlemi:
        oyuncuListesiView.setOnItemLongClickListener((parent, view, position, id) -> {
            String secilenOyuncu = oyuncuIsimleri.get(position);

            // Oyuncuyu kaldırmak için bir AlertDialog
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Oyuncuyu Kaldır")
                    .setMessage(secilenOyuncu + " oyuncusunu kaldırmak istediğinize emin misiniz?")
                    .setPositiveButton("Evet", (dialog, which) -> {
                        oyuncuIsimleri.remove(position); // Oyuncuyu kaldır
                        adapter.notifyDataSetChanged(); // Listeyi güncelle

                        // Eğer oyuncu sayısı 3'ün altına düşerse "Oyunu Başlat" butonunu gizle
                        if (oyuncuIsimleri.size() < 3) {
                            oyunuBaslatButonu.setVisibility(View.GONE);
                        }

                        Toast.makeText(MainActivity.this, secilenOyuncu + " kaldırıldı.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hayır", null)
                    .show();

            return true;
        });
        oyunuBaslatButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oyunuBaslat();
            }
        });
    }

    private void oyunuBaslat() {
        if (oyuncuIsimleri.size() >= 3) {
            Random random = new Random();

            // Rastgele seçilecek kelimeler
            String secilenKelime = kelimeler.get(random.nextInt(kelimeler.size()));
            String deliKelime = kelimeler.get(random.nextInt(kelimeler.size()));

            // Casus ve deli oyuncusunun indekslerini rastgele seçiyoruz
            int casusIndex = random.nextInt(oyuncuIsimleri.size());
            SharedPreferences sharedPreferences = getSharedPreferences("themePrefs", MODE_PRIVATE);
            boolean isDeli = sharedPreferences.getBoolean("isDeliRoleEnabled", false);


              int deliIndex = deli(isDeli);
                // Deli ve casus aynı oyuncu olmasın
                while (deliIndex == casusIndex) {
                    deliIndex = random.nextInt(oyuncuIsimleri.size());
                }

            // Oyunculara kelimeleri atıyoruz
            for (int i = 0; i < oyuncuIsimleri.size(); i++) {
                String oyuncuAdi = oyuncuIsimleri.get(i);

                // Casus oyuncusuna "Casus" kelimesi atanır
                if (i == casusIndex) {
                    oyuncuKelimeMapi.put(oyuncuAdi, "Casus");
                }

                // Deli oyuncusuna deliKelime atanır
                if (i == deliIndex) {
                    oyuncuKelimeMapi.put(oyuncuAdi, deliKelime);
                }
                // Diğer oyunculara normal kelime atanır
                if (i != casusIndex && i != deliIndex){
                    oyuncuKelimeMapi.put(oyuncuAdi, secilenKelime);
                }
            }

            // Tüm oyuncuların kelimelerini wordList'e ekliyoruz
            ArrayList<String> wordList = new ArrayList<>();
            for (int i = 0; i < oyuncuIsimleri.size(); i++) {
                wordList.add(oyuncuKelimeMapi.get(oyuncuIsimleri.get(i)));
            }

            // Sonraki oyuncuya geçiş yapıyoruz
            sonrakiOyuncuyaGec(0, wordList);

        } else {
            Toast.makeText(this, "En az 3 oyuncu gerekiyor!", Toast.LENGTH_SHORT).show();
        }
    }


    private void sonrakiOyuncuyaGec(int oyuncuIndex, ArrayList<String> wordList) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        intent.putStringArrayListExtra("oyuncuIsimleri", oyuncuIsimleri);
        intent.putExtra("siradakiOyuncuIndex", oyuncuIndex);
        intent.putStringArrayListExtra("wordList", wordList);
        startActivity(intent);
    }

    public int deli(boolean b){
        Random random = new Random();
        boolean c = random.nextBoolean();
        int a;
        if(b && c) {
            a = random.nextInt(oyuncuIsimleri.size());
            return a;
        }
        else{
            a = -1;
        }
        return a ;
    }
}
