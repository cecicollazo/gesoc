package com.gesoc.modelo.vinculador.condiciones;

import com.gesoc.modelo.vinculador.Vinculable;

public interface CondicionVinculacion {
    Boolean cumpleCondicion(Vinculable vinculable);
}
