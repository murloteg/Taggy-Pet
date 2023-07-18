package ru.nsu.sberlab.models.enums;

public enum Property {
    ALLERGY,
    FEEDING,
    MEDICINE,
    OTHER;

    public int getOrdinal() {
        return this.ordinal();
    }
}
