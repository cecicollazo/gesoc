package com.gesoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;
import com.gesoc.morphia.Cambios;
import com.gesoc.repositorios.DatosPrueba;
import com.gesoc.repositorios.RepositorioEgresos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

public class Resultado {
	

    public static Object resultados(Request request, Response response, String idEgreso) {

    	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); 
        //RepoOrganizacion repoOrg = new RepoOrganizacion();
        DatosPrueba repo = new DatosPrueba();
        ResultadoValidación validacion = new ResultadoValidación();
        DatosPrueba.egresos();
        long valor = Integer.parseInt(idEgreso);
        if (repo.byID(valor) != null) {
            validacion.setIdCompra(repo.byID(valor).getId());
            validacion.setNombreOrganizacion(repo.getOrg().getNombre());
            //validacion.setEgreso(repo.byID(valor));
            //validacion.presupuestoMenorCosto(DatosPrueba.presupuestos());
            validacion.fechaHoy();
            //validacion.validarCantidadPresupuesto(DatosPrueba.presupuestos());
            response.status(200);
            response.type("application/json");
            return gson.toJson(validacion);

        } else {

            response.status(400);
            ResultadoValidación validacion1 = new ResultadoValidación();
            validacion1.setError("No existe dicha compra");
            response.status(400);
            response.type("application/json");
            return gson.toJson(validacion1);
        }

    }
    
    
    
    public static Object mongoEgreso(Request request, Response response,EntityManager em) {
    	
    	 String id = request.queryParams("id");
    	 String tabla =  request.queryParams("tabla");
    	 Gson gson = new Gson(); 
    	 
    	 Cambios cambio = new Cambios();
    	 List<Cambios> mongos = cambio.TraerEnMongo();
    	    	 
    	 if (id==null && tabla==null) {
    		
    		 return gson.toJson(mongos);
        }
    	
    	 List<Cambios> result = null;
    	 if (id!=null && tabla==null) {
    		 Long LongId = Long.parseLong(id);
    	  result = mongos.stream().filter(a-> a.getId().equals(LongId)).collect(Collectors.toList());
    	  return gson.toJson(result);
    	 }
    	 if (id==null && tabla!=null) {
       	  result = mongos.stream().filter(a-> a.getTablaModificada().equals(tabla)).collect(Collectors.toList());
       	  return gson.toJson(result);
       	 }
    	 if (id!=null && tabla!=null) {
    		 long LongId = Integer.parseInt(id);
       	  result = mongos.stream().filter(a-> a.getId().equals(LongId)).collect(Collectors.toList());
       	  List<Cambios> result2 = result.stream().filter(a-> a.getTablaModificada().equals(tabla)).collect(Collectors.toList());
       	  return gson.toJson(result2);
       	 }
    	 return gson.toJson(mongos);
    	
        
    }
    
    public static Presupuesto setPresupuesto(ResultadoValidación validacion,List<Presupuesto> presupuestos) {
    	  
    	Presupuesto presupuestoElegido = validacion.presupuestoMenorCosto(presupuestos);
    	Presupuesto presupuestoElegido2 = new Presupuesto();
    	presupuestoElegido2.setProveedor(presupuestoElegido.getProveedor());
    	presupuestoElegido2.setId(presupuestoElegido.getId());
    	presupuestoElegido2.setÍtems(presupuestoElegido.getÍtems());
    	//presupuestoElegido2.setCategorías(presupuestoElegido.categorías());
    	presupuestoElegido2.setTotal(presupuestoElegido2.getMontoTotal());
		return presupuestoElegido2;
    	
    }
    public static Object resultadosSQL(Request request, Response response, String idEgreso,EntityManager em) {
    	//Con Gson Builder me trae la lista vacia
    	//Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    	//conGson Error Gson Overflow
    	Gson gson = new Gson();
        RepositorioEgresos repo = new RepositorioEgresos();
        ResultadoValidación validacion = new ResultadoValidación();
        long valor = Integer.parseInt(idEgreso);
        Egreso objectEgreso = repo.byIDEgresoSQL(valor);
        
        
        if ( objectEgreso != null) {
           validacion.setCode(200);
           validacion.setIdCompra(repo.byIDEgresoSQL(valor).getId());
           //validacion.setEgreso(objectEgreso);
           validacion.fechaHoy();    
           validacion.CantidadPresupuesto = objectEgreso.getPresupuestos().size();
           validacion.validarCantidadPresupuesto(objectEgreso.getPresupuestos());	
           if(validacion.CantidadPresupuesto > 3) {
        	   
        	  
        	   validacion.setCode(403);
               validacion.setError("Supera el limite maximo de 3 prespuestos a la vez");   
               response.status(403);         
               response.type("application/json");
               return gson.toJson(validacion);
           }
           
           if(!objectEgreso.getPresupuestos().isEmpty())
          {
        	
            //+-validacion.setPresupuestos(objectEgreso.getPresupuestos());
            Set<Item> itemsEgreso= objectEgreso.getItems();
             List<Presupuesto> presupuestos = objectEgreso.getPresupuestos();
            
            int  i = 0;
            int y = 0;
            int z = 1;
            String mensaje = "";
            Boolean valido = true;
            int diferencia = 0;
            //RECORRO ITEMS DE EGRESOS
			for( Iterator<Item> iE = itemsEgreso.iterator(); iE.hasNext();) { 
            	Item item =  iE.next();
            	 
            	 //RECORRO PRESUPUIESTOS
            	for(int k = 0; k < presupuestos.size(); k++){
        		   int nroPresu = y+1;
        		   
        		   Set<Item> itemsPresu= presupuestos.get(k).getÍtems();
        		   String nroPresuString = String.valueOf(nroPresu);
        		   z=1;
        		   for( Iterator<Item> iP = itemsPresu.iterator(); iP.hasNext();) { 
                   	Item item2 =  iP.next();
            	if(item.getProducto().getDescripcion().equals(item2.getProducto().getDescripcion())) {
            		item.getCantidad();
            		item2.getCantidad();
            		diferencia = item.getCantidad() - item2.getCantidad();
            		 String itemnro = String.valueOf(z);
            		
            		if(diferencia == 0) {           			
               			String mensaje2Actual = "Se compro una cantidad justa del producto "+item.getProducto().getDescripcion()+ " con respecto al itemNRO " + itemnro + " del PresupuestoNRO " + nroPresuString + " ; " ;
               			mensaje =  mensaje + mensaje2Actual;
               			
               		}
               		if(diferencia > 0) {           			
               			String mensaje2Actual = "Se compro una cantidad " + diferencia + " demas del producto "
               									+item.getProducto().getDescripcion()+ " con respecto al itemNRO " + itemnro + " del PresupuestoNRO " 
               										+ nroPresuString + " ; " ;
               			mensaje =  mensaje + mensaje2Actual;
               			
               		}
               		if(diferencia > 0) {  
               			String mensaje2Actual = "Se compro una cantidad " + diferencia + " de menos del producto "+item.getProducto().getDescripcion()+ " con respecto al itemNRO " + itemnro + " del PresupuestoNRO " + nroPresuString + " ; " ;
               			mensaje =  mensaje + mensaje2Actual;
               		}     		
            	}
            	
            	else {
            		valido = false;
            	}
            	
            	z++;
            	}
        		   
        		   if(valido ==false) {
                   	String mensajeagregado = "El PresupuestoNRO " + nroPresuString + " no coincide con la compra que se realizo ; " ;
               		mensaje =  mensaje + mensajeagregado;
               		presupuestos.get(k).setValido(false);
               		valido=true;
                   	}else {
                   		presupuestos.get(k).setValido(true);
                   	}
        		   
        		   
           		 y++;
            }
            i++;
            
            }
            
			Presupuesto presupuestoElegido = setPresupuesto(validacion,presupuestos);
        	validacion.setPresupuestoelegidoMenorCosto(presupuestoElegido);
            	
            validacion.setError(mensaje);
            response.status(200);
            response.type("application/json");            
            return gson.toJson(validacion);
            
            
           } else {
        	   ResultadoValidación validacion2 = new ResultadoValidación();
               validacion2.setCode(401);
               validacion2.setError("La compra no posee presupuesto");      
               response.status(401);         
               response.type("application/json");
               return gson.toJson(validacion2);
           }
           

        } else {
            ResultadoValidación validacion1 = new ResultadoValidación();
            validacion1.setCode(400);
            validacion1.setError("No existe dicha compra");      
            response.status(400);         
            response.type("application/json");
            return gson.toJson(validacion1);
        }
    }
    
    public static Object resultadosSQL2(Request request, Response response, String idEgreso,EntityManager em) throws JsonProcessingException {
    	//Con Gson Builder me trae la lista vacia
    	//Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); 
    	//conGson Error Gson Overflow
    	
    	
    	ObjectMapper mapper = new ObjectMapper(); 
    	
        RepositorioEgresos repo = new RepositorioEgresos();
        ResultadoValidación validacion = new ResultadoValidación();
        long valor = Integer.parseInt(idEgreso);
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
            response.status(200);
            response.type("application/json");
            // gson.toJson(validacion);
            String jsonResult = mapper.writeValueAsString(validacion);
            return jsonResult;
            
           } else {
        	   ResultadoValidación validacion2 = new ResultadoValidación();
               validacion2.setCode(401);
               validacion2.setError("La compra no posee presupuesto");      
               response.status(401);         
               response.type("application/json");
               //return gson.toJson(validacion2);
               String jsonResult = mapper.writeValueAsString(validacion2);
               return jsonResult;
           }
           

        } else {
            ResultadoValidación validacion1 = new ResultadoValidación();
            validacion1.setCode(400);
            validacion1.setError("No existe dicha compra");      
            response.status(400);         
            response.type("application/json");
            //return gson.toJson(validacion1);
            String jsonResult = mapper.writeValueAsString(validacion1);
            return jsonResult;
        }
    }
}
