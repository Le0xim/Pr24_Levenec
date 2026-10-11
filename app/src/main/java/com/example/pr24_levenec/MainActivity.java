package com.example.pr24_levenec;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 1. Объявляем переменные (убрали AssetManager, так как он не нужен для res/raw)
    private SoundPool mSoundPool;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Находим кнопки в макете
        ImageButton cowButton = findViewById(R.id.image_cow);
        ImageButton chickenButton = findViewById(R.id.image_chicken);
        ImageButton catButton = findViewById(R.id.image_cat);
        ImageButton duckButton = findViewById(R.id.image_duck);
        ImageButton sheepButton = findViewById(R.id.image_sheep);
        ImageButton dogButton = findViewById(R.id.image_dog);

        // 3. Устанавливаем обработчик нажатий
        cowButton.setOnClickListener(this);
        chickenButton.setOnClickListener(this);
        catButton.setOnClickListener(this);
        duckButton.setOnClickListener(this);
        sheepButton.setOnClickListener(this);
        dogButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Создаем SoundPool и загружаем звуки при возобновлении активности
        createNewSoundPool();

        // Передаем имя файла БЕЗ расширения (.mp3), так как это ресурс
        mCowSound = loadSound("cow");
        mChickenSound = loadSound("chicken");
        mCatSound = loadSound("cat");
        mDuckSound = loadSound("duck");
        mSheepSound = loadSound("sheep");
        mDogSound = loadSound("dog");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Освобождаем ресурсы SoundPool при паузе
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    // Метод для создания SoundPool (современный)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    // Метод для создания SoundPool (устаревший, оставим для совместимости по заданию)
    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    // Метод воспроизведения звука
    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    // ОБНОВЛЕННЫЙ МЕТОД загрузки звука из папки res/raw
    private int loadSound(String fileName) {
        // Получаем ID ресурса из папки res/raw по его имени
        int resId = getResources().getIdentifier(fileName, "raw", getPackageName());

        if (resId != 0) {
            // Если ресурс найден, загружаем его в SoundPool
            return mSoundPool.load(this, resId, 1);
        } else {
            // Если файл не найден, выводим сообщение об ошибке
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    // Обработка нажатий на кнопки
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_cow) {
            playSound(mCowSound);
        } else if (id == R.id.image_chicken) {
            playSound(mChickenSound);
        } else if (id == R.id.image_cat) {
            playSound(mCatSound);
        } else if (id == R.id.image_duck) {
            playSound(mDuckSound);
        } else if (id == R.id.image_sheep) {
            playSound(mSheepSound);
        } else if (id == R.id.image_dog) {
            playSound(mDogSound);
        }
    }
}