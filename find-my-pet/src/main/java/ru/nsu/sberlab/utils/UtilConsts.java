package ru.nsu.sberlab.utils;

public final class UtilConsts {
    public static final class StringConsts {
        public static final String INSTANTIATION_MESSAGE = "Instantiation of util class";
        public static final String MALE = "Самец";
        public static final String FEMALE = "Самка";

        private StringConsts() {
            throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
        }
    }

    private UtilConsts() {
        throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
    }
}
