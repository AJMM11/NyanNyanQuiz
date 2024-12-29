package com.example.nyannyanquiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nyannyanquiz.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    private TranslatorService transService;
    private ActivityQuizBinding binding;
    private List<Question> questions;

    private TextView questionTextView;
    private TextView option1TextView;
    private TextView option2TextView;
    private TextView option3TextView;
    private TextView option4TextView;

    private String ans1,ans2,ans3,ans4;
    private int score = 0;
    private String difficulty;
    private String language;

    private String translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Obtener dificultad del intent
        difficulty = getIntent().getStringExtra("difficulty");

        // Vincular vistas
        questionTextView = findViewById(R.id.quizText);
        option1TextView = findViewById(R.id.aanswer);
        option2TextView = findViewById(R.id.banswer);
        option3TextView = findViewById(R.id.canswer);
        option4TextView = findViewById(R.id.danswer);

        Locale currentLocale = Locale.getDefault();
        language = currentLocale.getLanguage();

        System.out.println("Language: " + language);
        if(language.equals("es")) {
            transService = RetrofitClient.getInstance().create(TranslatorService.class);

        }
        // Configurar preguntas
        setUp(difficulty);
    }

    private void setUp(String difficulty) {
        QuizApi quizApi = ApiClient.getRetrofitInstance().create(QuizApi.class);
        Call<ApiResponse> call = quizApi.getQuestions(10, 31, difficulty, "multiple");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questions = response.body().getResults();
                    displayQuestion(0);
                } else {
                    System.err.println("Failed to fetch questions");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                System.err.println("Error: " + t.getMessage());
            }
        });
    }
    private void translateAnswers(List<String> answers) {
        // Create a list of TranslateRequest objects for each answer
        List<TranslateRequest> translateRequests = new ArrayList<>();
        for (String answer : answers) {
            translateRequests.add(new TranslateRequest(answer));  // Assuming TranslateRequest is a model for the API
        }

        // Call the translate API (assuming you are translating to Spanish, change the language code as needed)
        transService.translateText(translateRequests).enqueue(new Callback<List<TranslateResponse>>() {
            @Override
            public void onResponse(Call<List<TranslateResponse>> call, Response<List<TranslateResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TranslateResponse> translatedResponses = response.body();

                    // Assuming the API returns translations in the order they were sent
                    for (int i = 0; i < translatedResponses.size(); i++) {
                        String translatedText = translatedResponses.get(i).getTranslations().get(0).getText();
                        answers.set(i, translatedText);  // Update the original answers list with translated text
                    }


                    option1TextView.setText(formatText(answers.get(0)));
                    option2TextView.setText(formatText(answers.get(1)));
                    option3TextView.setText(formatText(answers.get(2)));
                    option4TextView.setText(formatText(answers.get(3)));
                } else {
                    // Handle the error if the translation API call was unsuccessful
                    Toast.makeText(QuizActivity.this, "Translation failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TranslateResponse>> call, Throwable t) {
                // Handle the failure
                Toast.makeText(QuizActivity.this, "Translation request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion(int index) {
        if (questions != null && !questions.isEmpty() && index < questions.size()) {
            Question question = questions.get(index);

            if(language.equals("es"))
            {

                String textToTranslate = formatText(question.getQuestion());
                // Set up the request object
                TranslateRequest request = new TranslateRequest(textToTranslate);

                transService.translateText(Arrays.asList(request)).enqueue(new Callback<List<TranslateResponse>>() {
                    @Override
                    public void onResponse(Call<List<TranslateResponse>> call, Response<List<TranslateResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Get the translated text
                            translatedText = response.body().get(0).getTranslations().get(0).getText();
                            questionTextView.setText(formatText(translatedText));
                            //Toast.makeText(QuizActivity.this, "Translated Text: " + translatedText, Toast.LENGTH_LONG).show();
                        } else {
                            try{
                                System.err.println(response.errorBody().string());
                                Toast.makeText(QuizActivity.this, "Error: " + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                            }
                            catch(Exception e){
                                Log.e("Error", e.getMessage());
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<TranslateResponse>> call, Throwable t) {
                        Toast.makeText(QuizActivity.this, "Translation failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }else{
                questionTextView.setText(formatText(question.getQuestion()));
            }


            // Formatear y mostrar la pregunta

            questionTextView.setBackgroundColor(getResources().getColor(android.R.color.white));

            // Combinar y barajar respuestas
            List<String> answers = question.getIncorrectAnswer();
            if (answers == null) {
                answers = new ArrayList<>();
            }
            answers.add(question.getCorrectAnswer());
            Collections.shuffle(answers);


            // Asignar texto formateado a cada opción
            option1TextView.setText(formatText(answers.get(0)));
            option2TextView.setText(formatText(answers.get(1)));
            option3TextView.setText(formatText(answers.get(2)));
            option4TextView.setText(formatText(answers.get(3)));

            // Restablecer colores de las opciones
            resetOptionColors();

            // Configurar listeners para cada opción
            configureOptionClick(option1TextView, question, index);
            configureOptionClick(option2TextView, question, index);
            configureOptionClick(option3TextView, question, index);
            configureOptionClick(option4TextView, question, index);

        } else {
            System.out.println("El score es " + score);
            navigateToResultActivity();
        }
    }

    private void configureOptionClick(TextView optionTextView, Question question, int index) {
        optionTextView.setOnClickListener(v -> {
            runOnUiThread(() -> {
                if (optionTextView.getText().equals(formatText(question.getCorrectAnswer()))) {
                    optionTextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    score++;
                    Snackbar.make(v, getString(R.string.correct_test), Snackbar.LENGTH_SHORT).show();
                } else {
                    optionTextView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    highlightCorrectAnswer(question);
                    Snackbar.make(v, getString(R.string.incorrect), Snackbar.LENGTH_SHORT).show();
                }
            });

            optionTextView.postDelayed(() -> displayQuestion(index + 1), 1000);
        });
    }

    private void highlightCorrectAnswer(Question question) {
        String correctAnswer = formatText(question.getCorrectAnswer());
        if (option1TextView.getText().equals(correctAnswer)) {
            option1TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (option2TextView.getText().equals(correctAnswer)) {
            option2TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (option3TextView.getText().equals(correctAnswer)) {
            option3TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (option4TextView.getText().equals(correctAnswer)) {
            option4TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
    }

    private void resetOptionColors() {
        option1TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
        option2TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
        option3TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
        option4TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private String formatText(String text) {
        // Decodificar entidades HTML y eliminar espacios extra
        return android.text.Html.fromHtml(text).toString().trim();
    }

    private void navigateToResultActivity() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}
