package com.gesoc.modelo.medioDePago;

import org.json.JSONArray;
import org.json.JSONObject;

public interface InfoMedioDePago {
    public JSONArray obtenerMediosDePago();

    public JSONObject obtenerJSONMedioDePago(String MedioDePago);

}
