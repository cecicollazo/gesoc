package com.gesoc.modelo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gesoc.modelo.categorización.Categorizable;
import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.operaciones.DocumentoComercial;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Presupuesto implements Categorizable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private final Set<DocumentoComercial> documentosComerciales = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private  Set<Item> ítems = new HashSet<>();
    @OneToOne (cascade = {CascadeType.ALL})
    private Proveedor proveedor;
    @ManyToMany (cascade=CascadeType.ALL)
    private Set<Categoría> categorías = new HashSet<>();
    private double total;
    private Boolean valido;
    // TODO: validar null
    public Presupuesto(final Collection<DocumentoComercial> documentosComerciales,
                       final Collection<Item> ítems,
                       final Proveedor proveedor,
                       final Collection<Categoría> categorías) {
        this.documentosComerciales.addAll(documentosComerciales);
        this.ítems.addAll(ítems);
        this.proveedor = proveedor;
        this.categorías.addAll(categorías);
    }

    public void agregarDocumentoComercial(final DocumentoComercial documento) {
        documentosComerciales.add(documento);
    }

    public void agregarÍtem(final Item ítem) {
        ítems.add(ítem);
    }

    public double getMontoTotal() {
        return ítems.stream().mapToInt(Item::getPrecioTotal).sum();
    }
    
    public double montoTotal() {
		total = ítems.stream().mapToInt(e -> e.getPrecio()).sum();
		return total;
	}
    
    


    @Override
    public Set<Categoría> categorías() {
        return categorías;
    }
    
    public void setCategorías(Set<Categoría> cate) {
         this.categorías = cate;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DocumentoComercial> getDocumentosComerciales() {
        return documentosComerciales;
    }

    

    public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Set<Item> getÍtems() {
		return ítems;
	}
	public Set<Item> setÍtems(Set<Item> ítems) {
		return this.ítems = ítems;
	}

	public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Set<Categoría> getCategorías() {
        return categorías;
    }

    public Presupuesto() {
    }

	public Boolean getValido() {
		return valido;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}
}
