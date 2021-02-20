package com.gesoc.repositorios;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.modelo.operaciones.Egreso;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

class Repositorio<T> {
    public static final EntityManager ENTITY_MANAGER;

    private final Class<T> clase;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String persistenceUnit = System.getenv("PERSISTENCE_UNIT");
        if (Objects.isNull(persistenceUnit)) {
            persistenceUnit = "prod";
        }
        ENTITY_MANAGER = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
    }

    protected Repositorio(final Class<T> clase) {
        this.clase = clase;
    }

    public void guardar(final T objeto) {
        try {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.persist(objeto);
            ENTITY_MANAGER.getTransaction().commit();
        } catch (Exception e) {
            ENTITY_MANAGER.getTransaction().rollback();
            throw new RuntimeException("Error al persistir: ", e);
        }
    }

    public T obtenerById(final Long id) {
        return ENTITY_MANAGER.find(clase, id);
    }

    public void borrar(final T objeto) {
        try {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.remove(objeto);
            ENTITY_MANAGER.getTransaction().commit();
        } catch (Exception e) {
            ENTITY_MANAGER.getTransaction().rollback();
            throw new RuntimeException("Error al borrar: ", e);
        }
    }
    
    public void borrarEgreso(final Egreso egreso) {
        try {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.remove(egreso);
            ENTITY_MANAGER.getTransaction().commit();
        } catch (Exception e) {
            ENTITY_MANAGER.getTransaction().rollback();
            throw new RuntimeException("Error al borrar: ", e);
        }
    }

    public void borrarPorId(final Long id) {
    	RepositorioEgresos E = new RepositorioEgresos();
    	Egreso egreso = E.byIDEgresoSQL(id);
    	borrarEgreso(egreso);
    }

    protected Optional<T> obtenerPor(final Predicate<? super T> predicado) {
        return obtenerTodos().stream().filter(predicado).findFirst();
    }

    protected List<T> obtenerTodos() {
        return allQuery().getResultList();
    }

    protected List<T> obtenerPaginado(final int offset, final int cantidadPorPágina) {
        return allQuery().setFirstResult(offset).setMaxResults(cantidadPorPágina).getResultList();
    }

    protected List<T> obtenerPaginadoCondición(final int offset, final int cantidadPorPágina, final String campo,
                                               final String valor) {
        return whereOrderQuery(campo, valor, "id").setFirstResult(offset).setMaxResults(cantidadPorPágina)
                .getResultList();
    }

    protected List<T> obtenerPorCondición(final String campo, final String valor) {
        return whereQuery(campo, valor).getResultList();
    }

    public long cantidadPorCondición(final String campo, final String valor) {
        return whereQuery(campo, valor).getResultStream().count();
    }

    protected List<T> obtenerConCondición(final String campo, final String valor) {
        return whereQuery(campo, valor).getResultList();
    }

    private TypedQuery<T> allQuery() {
        final CriteriaBuilder cb = ENTITY_MANAGER.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(clase);
        final Root<T> rootEntry = cq.from(clase);
        final CriteriaQuery<T> all = cq.select(rootEntry);
        return ENTITY_MANAGER.createQuery(all);
    }

    private TypedQuery<T> whereQuery(final String campo, final String valor) {
        final CriteriaBuilder cb = ENTITY_MANAGER.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(clase);
        final Root<T> rootEntry = cq.from(clase);
        cq.where(cb.equal(rootEntry.get(campo), valor));
        return ENTITY_MANAGER.createQuery(cq);
    }

    private TypedQuery<T> whereOrderQuery(final String campo, final String valor, final String campoOrden) {
        final CriteriaBuilder cb = ENTITY_MANAGER.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(clase);
        final Root<T> rootEntry = cq.from(clase);
        cq.where(cb.equal(rootEntry.get(campo), valor));
        cq.orderBy(cb.asc(rootEntry.get(campoOrden)));
        return ENTITY_MANAGER.createQuery(cq);
    }
}
