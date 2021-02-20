package com.gesoc.modelo.operaciones;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gesoc.modelo.medioDePago.MedioDePago;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.validador.CriterioSelecciónMenorCosto;
import com.gesoc.modelo.validador.CriterioValidación;
import com.gesoc.modelo.validador.CriterioValidaciónCantidadPresupuestos;
import com.gesoc.modelo.resultado.ResultadoValidación;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Egreso extends Operación {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private DocumentoComercial documentoComercial;
    @OneToOne
    private Proveedor proveedor;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();
    private Boolean requierePresupuesto;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Presupuesto> presupuestos = new ArrayList<>();
    @OneToOne
    private Presupuesto presupuestoElegido;
    private LocalDate fehaAceptabilidadDesde;
    private LocalDate fehaAceptabilidadHasta;
    @OneToOne(cascade = CascadeType.ALL)
    private MedioDePago medioDePago;
    private int cantidadPresupuestosRequeridos;
    @Transient
    private Set<CriterioValidación> criteriosValidación = new HashSet<>(
            Arrays.asList(new CriterioValidaciónCantidadPresupuestos(), new CriterioSelecciónMenorCosto()));

    public Egreso(final DocumentoComercial documentoComercial,
                  final LocalDate fecha,
                  final Proveedor proveedor,
                  final Set<Item> items,
                  final Boolean requierePresupuesto,
                  final Set<Presupuesto> presupuestos,
                  final Presupuesto presupuestoElegido,
                  final LocalDate fehaAceptabilidadDesde,
                  final LocalDate fehaAceptabilidadHasta,
                  final MedioDePago medioDePago,
                  final int cantidadPresupuestosRequeridos,
                  final Set<CriterioValidación> criteriosValidación) {
        super(fecha);
        this.documentoComercial = documentoComercial;
        this.proveedor = proveedor;
        this.items.addAll(items);
        this.requierePresupuesto = requierePresupuesto;
        this.presupuestos.addAll(presupuestos);
        this.presupuestoElegido = presupuestoElegido;
        this.fehaAceptabilidadDesde = fehaAceptabilidadDesde;
        this.fehaAceptabilidadHasta = fehaAceptabilidadHasta;
        this.medioDePago = medioDePago;
        this.cantidadPresupuestosRequeridos = cantidadPresupuestosRequeridos;
        this.criteriosValidación.addAll(criteriosValidación);
    }

    public Egreso(Proveedor proveedor, DocumentoComercial documentoComercial) {
        super(LocalDate.now());
        this.proveedor = proveedor;
        this.documentoComercial = documentoComercial;
    }

    public Set<ResultadoValidación> resultadosValidación() {
        return requierePresupuesto
                ? criteriosValidación.stream()
                .map(criterioValidación -> criterioValidación.resultadoValidación(this))
                .collect(Collectors.toSet())
                : Collections.emptySet();
    }

    public int getMontoTotal(){
        return this.items.stream().mapToInt(Item::getPrecioTotal).sum();
    }

    public String getTipoOperacion(){
        return "Egreso";
    }

    public void agregarPresupuesto(final Presupuesto presupuesto) {
        presupuestos.add(presupuesto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer calcularValor() {
        return this.items.stream().mapToInt(Item::getPrecioTotal).sum();
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public void cargarItem(Item item) {
        this.items.add(item);
    }

    public DocumentoComercial getDocumentoComercial() {
        return documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Boolean getRequierePresupuesto() {
        return requierePresupuesto;
    }

    public void setRequierePresupuesto(Boolean requierePresupuesto) {
        this.requierePresupuesto = requierePresupuesto;
    }

    public Presupuesto getPresupuestoElegido() {
        return presupuestoElegido;
    }

    public void setPresupuestoElegido(Presupuesto presupuestoElegido) {
        this.presupuestoElegido = presupuestoElegido;
    }

    public LocalDate getFehaAceptabilidadDesde() {
        return fehaAceptabilidadDesde;
    }

    public void setFehaAceptabilidadDesde(LocalDate fehaAceptabilidadDesde) {
        this.fehaAceptabilidadDesde = fehaAceptabilidadDesde;
    }

    public LocalDate getFehaAceptabilidadHasta() {
        return fehaAceptabilidadHasta;
    }

    public void setFehaAceptabilidadHasta(LocalDate fehaAceptabilidadHasta) {
        this.fehaAceptabilidadHasta = fehaAceptabilidadHasta;
    }

    public Integer getTotal() {
        return this.calcularValor();
    }

    @Override
    public String toString() {
        return "Egreso{" +
                "documentoComercial=" + documentoComercial +
                ", proveedor=" + proveedor +
                ", medioDePago=" + medioDePago +
                ", items=" + items +
                ", requierePresupuesto=" + requierePresupuesto +
                ", presupuestoElegido=" + presupuestoElegido +
                ", fehaAceptabilidadDesde=" + fehaAceptabilidadDesde +
                ", fehaAceptabilidadHasta=" + fehaAceptabilidadHasta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Egreso egreso = (Egreso) o;
        return Objects.equals(documentoComercial, egreso.documentoComercial) &&
                Objects.equals(proveedor, egreso.proveedor) &&
                Objects.equals(medioDePago, egreso.medioDePago) &&
                Objects.equals(items, egreso.items) &&
                Objects.equals(requierePresupuesto, egreso.requierePresupuesto) &&
                Objects.equals(presupuestoElegido, egreso.presupuestoElegido) &&
                Objects.equals(fehaAceptabilidadDesde, egreso.fehaAceptabilidadDesde) &&
                Objects.equals(fehaAceptabilidadHasta, egreso.fehaAceptabilidadHasta);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(documentoComercial, proveedor, medioDePago, items, requierePresupuesto, presupuestoElegido,
                        fehaAceptabilidadDesde, fehaAceptabilidadHasta);
    }

    public List <Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(List<Presupuesto> set) {
         presupuestos = set;
    }
    
    public void setCantidadPresupuestosRequeridos(final int cantidadPresupuestosRequeridos) {
        this.cantidadPresupuestosRequeridos = cantidadPresupuestosRequeridos;
    }

    public int getCantidadPresupuestosRequeridos() {
        return cantidadPresupuestosRequeridos;
    }

    public Egreso() {
    }
}
