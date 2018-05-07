package com.example.fsk.hw2;

public class Memory {
    String content;
    String link;

    public Memory(String content, String link) {
        this.content = content;
        this.link = link;
    }

    @Override
    public String toString() {
        return content;
    }
}
