package com.gesoc.modelo.resultado;

        import java.util.List;
import java.util.Set;

import com.gesoc.modelo.Presupuesto;


public class ResultadoValidaciónCantidadPresupuestos {

    private static Integer cantidadRequerida;
    private static Integer cantidadReal;

    public static Boolean verificarCantidad(List<Presupuesto> set) {
        cantidadReal=3;
        cantidadRequerida = set.size();
        if (cantidadReal >= cantidadRequerida) {

            return true;
        } else {

            return false;
        }
    }

   

    public static Integer setCantidadReal(int valor) {
        cantidadReal = valor;
        return cantidadReal;

    }

    public Integer getCantidadReal() {
        return cantidadReal;

    }

}
