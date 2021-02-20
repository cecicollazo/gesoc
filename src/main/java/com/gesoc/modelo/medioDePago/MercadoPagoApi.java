package com.gesoc.modelo.medioDePago;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class MercadoPagoApi implements InfoMedioDePago {

    public ClientResponse obtenerJSON(String Url) {
        String appKey = "Bearer ACCESS_TOKEN_ENV";
        ClientResponse data = Client.create()
                .resource("https://api.mercadopago.com/")
                .path(Url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", appKey)
                .get(ClientResponse.class);
        return data;
    }

    public ClientResponse obtenerJSON(String Url,MultivaluedMap<String,String> params) {
        ClientResponse data = Client.create()
                .resource("https://api.mercadopago.com/")
                .path(Url)
                .queryParams(params)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        return data;
    }

    public JSONObject obtenerJSONEspecifico(String objetivo, String enlace, JSONArray jsonArray) {
        String idObjetivo = null;

        for(int i= 0; i < jsonArray.length();i++) {
            JSONObject jsonObjetivo = jsonArray.getJSONObject(i);

            if(jsonObjetivo.getString("name").equals(objetivo))
                idObjetivo = jsonObjetivo.getString("id");
        }

        return new JSONObject(obtenerJSON(enlace + idObjetivo).getEntity(String.class));
    }

    public JSONObject obtenerJSONMedioDePago(String MedioDePago) {

        ClientResponse data = obtenerJSON("/sites/MLA/payment_methods");
        JSONArray jsonArray = new JSONArray(data.getEntity(String.class));
        return obtenerJSONEspecifico(MedioDePago, "/v1/payment_methods", jsonArray);
    }

    public JSONArray obtenerMediosDePago(){
        return new JSONArray(obtenerJSON("/sites/MLA/payment_methods").getEntity(String.class));
    }

    public List<String> obtenerListaMediosDePago(){
        List<String> mediosDePago = new ArrayList<String>();

        JSONArray array = this.obtenerMediosDePago();
        for(int i = 0 ;i < array.length() ;i++) {
            JSONObject item = array.getJSONObject(i);
            mediosDePago.add((String)item.get("name"));
        }

        return mediosDePago;
    }

}
