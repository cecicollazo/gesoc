package com.gesoc.modelo.resultado;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.gesoc.modelo.Presupuesto;


public class ResultadoValidaciónSelecciónPresupuesto {
	
	 public Presupuesto presupuestoElegido(List<Presupuesto> presupuesto) {

	        return ResultadoValidaciónSelecciónPresupuesto.menorCosto(presupuesto);

	    }

    public static Presupuesto menorCosto(List<Presupuesto> considerados) {
    	
    	
    	 List<Presupuesto> result = considerados.stream().filter(a-> a.getValido().equals(true)).collect(Collectors.toList());
    	 result.stream().sorted(Comparator.comparingDouble(Presupuesto::getMontoTotal)).collect(Collectors.toList());
        
    	Presupuesto pre = result.get(0);
        return pre;

    }

}