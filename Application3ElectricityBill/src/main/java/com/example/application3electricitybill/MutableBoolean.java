package com.example.application3electricitybill;

public class MutableBoolean {
    boolean value;

    public MutableBoolean(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        this.value = value;
    }
}
