package com.example.nyannyanquiz;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TranslateResponse {

    @SerializedName("translations")
    private List<Translation> translations;

    public List<Translation> getTranslations() {
        return translations;
    }

    public static class Translation {
        private String text;
        private String to;

        public String getText() {
            return text;
        }

        public String getTo() {
            return to;
        }
    }
}
