package com.example.nyannyanquiz;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AzureTranslator {

    // Clave de suscripción de Azure y el endpoint
    private static final String SUBSCRIPTION_KEY = "5LwJffQGY6ByfHc7hdW0618Y4HliQuqwnAtLDBIq5Jj1fdYxOJSYJQQJ99ALAC5RqLJXJ3w3AAAbACOGoNYi";
    private static final String ENDPOINT = "https://api.cognitive.microsofttranslator.com"; // Cambiar si el endpoint es diferente
    private static final String REGION = "westeurope"; // Cambia si configuraste una región específica

    // Método para traducir texto de forma sincrónica
    public String translateText(String text, String targetLanguage) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Construir la URL
        String url = ENDPOINT + "/translate?api-version=3.0&to=" + targetLanguage;

        // Crear el cuerpo de la solicitud en formato JSON
        JSONArray requestBody = new JSONArray();
        JSONObject textObject = new JSONObject();
        try {
            textObject.put("Text", text);
            requestBody.put(textObject);
        } catch (Exception e) {
            throw new IOException("Error creando el cuerpo de la solicitud", e);
        }

        // Configurar el cuerpo de la solicitud
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestBody.toString()
        );

        // Crear la solicitud HTTP
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY) // Clave de Azure
                .addHeader("Ocp-Apim-Subscription-Region", REGION)        // Región configurada
                .post(body)
                .build();

        // Ejecutar la solicitud de manera sincrónica
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                try {
                    // Parsear el JSON de respuesta
                    JSONArray translations = new JSONArray(responseBody)
                            .getJSONObject(0)
                            .getJSONArray("translations");

                    // Extraer el texto traducido
                    return translations.getJSONObject(0).getString("text");
                } catch (Exception e) {
                    throw new IOException("Error parseando la respuesta de traducción", e);
                }
            } else {
                throw new IOException("Translation failed: " + response.message());
            }
        }
    }
}
