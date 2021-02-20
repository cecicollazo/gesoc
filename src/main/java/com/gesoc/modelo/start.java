package com.gesoc.modelo;

import static spark.Spark.port;
import static spark.debug.DebugScreen.enableDebugScreen;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;
import com.gesoc.repositorios.DatosPrueba;
import com.gesoc.repositorios.RepositorioEgresos;
import com.gesoc.repositorios.RepositorioPresupuestos;
import com.gesoc.server.Router;
import com.gesoc.services.Resultado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Spark;

public class start {

	static EntityManagerFactory entityManagerFactory;
   



    private static void initPersistance() {
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }
	 public static void main( String[] args) {
	        
		 initPersistance();
		 
		 
			//Con Gson Builder me trae la lista vacia
	    	//Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); 
	    	//conGson Error Gson Overflow
	    	Gson gson = new Gson(); 
	        RepositorioEgresos repo = new RepositorioEgresos();
	        ResultadoValidación validacion = new ResultadoValidación();
	        long valor = Integer.parseInt("109");
	        Egreso objectEgreso = repo.byIDEgresoSQL(valor);
	        if ( objectEgreso != null) {
	           validacion.setCode(200);
	           validacion.setIdCompra(repo.byIDEgresoSQL(valor).getId());
	           //validacion.setEgreso(objectEgreso);
	           validacion.fechaHoy();    
	           validacion.CantidadPresupuesto = objectEgreso.getPresupuestos().size();
	           
	           if(!objectEgreso.getPresupuestos().isEmpty())
	          {
	        	Presupuesto presupuestoElegido = validacion.presupuestoMenorCosto(objectEgreso.getPresupuestos());
	        	validacion.setPresupuestoelegidoMenorCosto(presupuestoElegido);
	            validacion.validarCantidadPresupuesto(objectEgreso.getPresupuestos());
	           
	           
	            
	            System.out.printf("Ahora %s", gson.toJson(validacion));
	            
	            
	           } else {
	        	   ResultadoValidación validacion2 = new ResultadoValidación();
	               validacion2.setCode(401);
	               validacion2.setError("La compra no posee presupuesto");      
	              
	               System.out.printf("Ahora %s", gson.toJson(validacion2));
	           }
	           

	        } else {
	            ResultadoValidación validacion1 = new ResultadoValidación();
	            validacion1.setCode(400);
	            validacion1.setError("No existe dicha compra");      
	           
	            System.out.printf("Ahora %s", gson.toJson(validacion1));
	        }
		 
		 
		 
		 
	    }
}
