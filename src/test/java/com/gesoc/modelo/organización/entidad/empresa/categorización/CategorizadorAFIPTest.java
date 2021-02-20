package com.gesoc.modelo.organización.entidad.empresa.categorización;

import com.gesoc.modelo.organización.entidad.empresa.Empresa;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;

import static com.gesoc.modelo.organización.entidad.empresa.Sector.SERVICIOS;
import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.GRANDE;
import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.MICRO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategorizadorAFIPTest {
    private static final class CriterioMockMicro implements CriterioCategorizaciónAFIP {
        public CategoríaAFIP categoría(Empresa empresa) {
            return MICRO;
        }
    }

    private static final class CriterioMockGrande implements CriterioCategorizaciónAFIP {
        public CategoríaAFIP categoría(Empresa empresa) {
            return GRANDE;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void unCategorizadorSinCriteriosEsInválido() {
        CategorizadorAFIP categorizador = new CategorizadorAFIP(new HashSet<>());
    }

    @Test
    public void unCategorizadorConUnCriterioEsVálido() {
        CriterioCategorizaciónAFIP criterio = new CriterioMockMicro();
        CategorizadorAFIP categorizador = new CategorizadorAFIP(Sets.newHashSet(criterio));

        assertNotNull(categorizador);
    }

    @Test
    public void unCategorizadorConUnCriterioCategorizaSegúnEseCriterio() {
        CriterioCategorizaciónAFIP criterio = new CriterioMockMicro();
        CategorizadorAFIP categorizador = new CategorizadorAFIP(Sets.newHashSet(criterio));
        Empresa empresa = new Empresa(SERVICIOS, 100, 1000, categorizador);

        assertEquals(MICRO, categorizador.categorizar(empresa));
    }

    @Test
    public void unCategorizadorConMásDeUnCriterioCategorizaSegúnLaMáximaCategoríaDeSusCriterios() {
        CriterioCategorizaciónAFIP criterioMicro = new CriterioMockMicro();
        CriterioCategorizaciónAFIP criterioGrande = new CriterioMockGrande();
        CategorizadorAFIP categorizador = new CategorizadorAFIP(Sets.newHashSet(criterioMicro, criterioGrande));
        Empresa empresa = new Empresa(SERVICIOS, 100, 1000, categorizador);

        assertEquals(GRANDE, categorizador.categorizar(empresa));
    }
}
