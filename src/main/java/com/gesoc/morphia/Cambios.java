package com.gesoc.morphia;

import com.gesoc.modelo.operaciones.Egreso;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Cambios {
	private Long id;
    private String user;
    private LocalDateTime date;
    private String tablaModificada;
    private Ejecucion ejecucion;

    public void cargarEnMongo(String nombreUsuario, String tablaModificada, Ejecucion ejecucion,Long id) {

        Cambios cambios = new Cambios();
        cambios.setId(id);
        cambios.setUser(nombreUsuario);
        cambios.setEjecucion(ejecucion);
        cambios.setDate(LocalDateTime.now());
        cambios.setTablaModificada(tablaModificada);

        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://Damian:Damian123@cluster0.vwku9.mongodb.net/dds?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("dds");

        Datastore datastore = morphia.createDatastore(mongoClient, "dds");
        datastore.ensureIndexes();

        datastore.save(cambios);
    }
    
    public void setId(Long id) {
    	this.id = id;
		// TODO Auto-generated method stub
		
	}
    public Long getId() {
    	return id;
    }
    public String getTablaModificada() {
    	return tablaModificada;
    }

	public List<Cambios> TraerEnMongo() {

    

        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://Damian:Damian123@cluster0.vwku9.mongodb.net/dds?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("dds");

        Datastore datastore = morphia.createDatastore(mongoClient, "dds");
        datastore.ensureIndexes();

        List<Cambios> books = datastore.createQuery(Cambios.class)
      		  .find()
      		  .toList();
        
        return books;
    }
	
	
    
   


    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }


    public LocalDateTime getDate() {
        return date;
    }


    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Ejecucion getEjecucion() {
        return ejecucion;
    }

    public void setTablaModificada(String tablaModificada) {
        this.tablaModificada = tablaModificada;
    }

    public void setEjecucion(Ejecucion ejecucion) {
        this.ejecucion = ejecucion;
    }


}
