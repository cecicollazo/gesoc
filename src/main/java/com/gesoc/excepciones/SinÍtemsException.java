package com.gesoc.excepciones;

public class SinÍtemsException extends GeSocException {
    private static final long serialVersionUID = 7395564910983677412L;

    public SinÍtemsException() {
        super("Ítems vacíos");
    }
}
