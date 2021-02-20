package com.gesoc.repositorios;


import com.gesoc.modelo.operaciones.Egreso;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioEgresos extends RepositorioOperaciones<Egreso> {

    public RepositorioEgresos() {
        super(Egreso.class);
    }

    public Egreso byIDEgresoSQL(long id) {
        if (this.existsEgreso(id)) {
            CriteriaBuilder cb = ENTITY_MANAGER.getCriteriaBuilder();
            CriteriaQuery<Egreso> consulta = cb.createQuery(Egreso.class);
            Root<Egreso> egresos = consulta.from(Egreso.class);
            Predicate condicion = cb.equal(egresos.get("id"), id);
            CriteriaQuery<Egreso> where = consulta.select(egresos).where(condicion);
            return ENTITY_MANAGER.createQuery(where).getSingleResult();
        }
        return null;
 }

    public boolean existsEgreso(long id) {
        CriteriaBuilder cb = ENTITY_MANAGER.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Egreso> egresos = consulta.from(Egreso.class);
        Predicate condicion = cb.equal(egresos.get("id"), id);
        CriteriaQuery<Long> select = consulta.where(condicion).select(cb.count(egresos));
        return ENTITY_MANAGER.createQuery(select).getSingleResult() > 0;
    }

    @Override
    public List<Egreso> obtenerTodos() {
        return super.obtenerTodos();
    }
}
