package com.example.nyannyanquiz;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nyannyanquiz.databinding.ActivityQuizBinding;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityQuizBinding binding;

    private List<Question> questions;

    private TextView questionTextView;
    private TextView option1TextView;
    private TextView option2TextView;
    private TextView option3TextView;
    private TextView option4TextView;
    private int score=0;

    private void navigateToResultActivity() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score); // Pass the score to ResultActivity
        startActivity(intent);
    }
    public void setUp(String dif) {
        QuizApi quizApi = ApiClient.getRetrofitInstance().create(QuizApi.class);
        Call<ApiResponse> call = quizApi.getQuestions(10, 31, dif, "multiple");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questions = response.body().getResults();
                    System.out.println(questions.get(0).getQuestion());

                    System.out.println("Questions fetched: " + questions.size());
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

    private void highlightCorrectAnswer(Question question) {
        if (option1TextView.getText().equals(question.getCorrectAnswer())) {
            option1TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (option2TextView.getText().equals(question.getCorrectAnswer())) {
            option2TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (option3TextView.getText().equals(question.getCorrectAnswer())) {
            option3TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else if (option4TextView.getText().equals(question.getCorrectAnswer())) {
            option4TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
    }
    private void displayQuestion(int index) {
        if (questions != null && !questions.isEmpty() && index < questions.size()) {
            Question question = questions.get(index);
            questionTextView.setText(question.getQuestion());
            questionTextView.setBackgroundColor(getResources().getColor(android.R.color.white));

            List<String> answers = question.getIncorrectAnswer();
            if (answers == null) {
                answers = new ArrayList<>();
            }
            answers.add(question.getCorrectAnswer());
            Collections.shuffle(answers);

            option1TextView.setText(answers.get(0));
            option2TextView.setText(answers.get(1));
            option3TextView.setText(answers.get(2));
            option4TextView.setText(answers.get(3));

            option1TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
            option2TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
            option3TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
            option4TextView.setBackgroundColor(getResources().getColor(android.R.color.white));
            option1TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (option1TextView.getText().equals(question.getCorrectAnswer())) {
                                option1TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                                option1TextView.invalidate(); // Force redraw
                                option1TextView.requestLayout(); // Request layout update
                                score++;
                                Snackbar.make(v, "Correct!", Snackbar.LENGTH_SHORT).show();
                            } else {
                                option1TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                option1TextView.invalidate(); // Force redraw
                                option1TextView.requestLayout(); // Request layout update
                                highlightCorrectAnswer(question);
                                Snackbar.make(v, "Incorrect!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                    option1TextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            displayQuestion(index + 1);
                        }
                    }, 1000);

                }
            });
            option2TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (option2TextView.getText().equals(question.getCorrectAnswer())) {
                                option2TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                                option2TextView.invalidate(); // Force redraw
                                option2TextView.requestLayout(); // Request layout update
                                score++;
                                Snackbar.make(v, "Correct!", Snackbar.LENGTH_SHORT).show();
                            } else {
                                option2TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                option2TextView.invalidate(); // Force redraw
                                option2TextView.requestLayout(); // Request layout update
                                highlightCorrectAnswer(question);
                                Snackbar.make(v, "Incorrect!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                    option2TextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            displayQuestion(index + 1);
                        }
                    }, 1000);

                }
            });
            option3TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (option3TextView.getText().equals(question.getCorrectAnswer())) {
                                option3TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                                option3TextView.invalidate(); // Force redraw
                                option3TextView.requestLayout(); // Request layout update
                                score++;
                                Snackbar.make(v, "Correct!", Snackbar.LENGTH_SHORT).show();
                            } else {
                                option3TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                option3TextView.invalidate(); // Force redraw
                                option3TextView.requestLayout(); // Request layout update
                                highlightCorrectAnswer(question);
                                Snackbar.make(v, "Incorrect!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                    option3TextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            displayQuestion(index + 1);
                        }
                    }, 1000);

                }
            });
            option4TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (option4TextView.getText().equals(question.getCorrectAnswer())) {
                                option4TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                                option4TextView.invalidate(); // Force redraw
                                option4TextView.requestLayout(); // Request layout update
                                score++;
                                Snackbar.make(v, "Correct!", Snackbar.LENGTH_SHORT).show();
                            } else {
                                option4TextView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                option4TextView.invalidate(); // Force redraw
                                option4TextView.requestLayout(); // Request layout update
                                highlightCorrectAnswer(question);
                                Snackbar.make(v, "Incorrect!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                    option4TextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            displayQuestion(index + 1);
                        }
                    }, 1000);

                }
            });

        }else{
            System.out.println("El score es " + score);
            navigateToResultActivity();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        String difficulty = getIntent().getStringExtra("difficulty");
        questionTextView = findViewById(R.id.quizText);
        option1TextView = findViewById(R.id.aanswer);
        option2TextView = findViewById(R.id.banswer);
        option3TextView = findViewById(R.id.canswer);
        option4TextView = findViewById(R.id.danswer);
        setUp(difficulty);


        //System.out.println("La primera opcion es " +option1TextView.getText());

    }
}