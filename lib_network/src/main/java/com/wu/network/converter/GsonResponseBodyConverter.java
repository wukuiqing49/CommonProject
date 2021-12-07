package com.wu.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.wu.network.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private boolean decode;
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, boolean decode) {
        this.gson = gson;
        this.adapter = adapter;
        this.decode = decode;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        if (decode) {
            try {
                return adapter.fromJson(convert(value.charStream()));
            } finally {
                value.close();
            }
        } else {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }

    private static String convert(Reader reader) throws IOException {
        BufferedReader r = new BufferedReader(reader);
        StringBuilder b = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) b.append(line);
        return StringUtils.decode(b.toString());
    }
}