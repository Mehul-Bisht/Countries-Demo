package com.example.countries.data.network;

import com.google.gson.annotations.SerializedName;

public class languageParams {

    @SerializedName("iso639_1")
    String symbol;
    String name;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
