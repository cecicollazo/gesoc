package com.gesoc.mappers;

import com.gesoc.dto.balance.BalanceDTO;
import com.gesoc.dto.balance.VinculableDTO;
import com.gesoc.modelo.vinculador.Vinculable;
import com.gesoc.modelo.vinculador.balances.Balance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VinculadorMapper {

    public List<BalanceDTO> mapearRespuestaBalance(Set<? extends Balance<?, ?>> balances){

        List<BalanceDTO> resultadoFinalBalances = new ArrayList<>();

        for (Balance<?, ?> balance : balances) {
            BalanceDTO balanceDTO = new BalanceDTO();

            balanceDTO.setFechaVinculacion(balance.getFechaVinculacion());

            VinculableDTO vinculableDTO = new VinculableDTO();
            vinculableDTO.setId(balance.getVinculable().getId());
            vinculableDTO.setMontoTotal(balance.getVinculable().getMontoTotal());
            vinculableDTO.setTipoOperacion(balance.getVinculable().getTipoOperacion());
            balanceDTO.setVinculable(vinculableDTO);

            List<VinculableDTO> listaVinculables = new ArrayList<>();

            for (Vinculable vinculable : balance.getVinculables()) {
                VinculableDTO vinculable2DTO = new VinculableDTO();
                vinculable2DTO.setId(vinculable.getId());
                vinculable2DTO.setMontoTotal(vinculable.getMontoTotal());
                vinculable2DTO.setTipoOperacion(vinculable.getTipoOperacion());
                listaVinculables.add(vinculable2DTO);
            }
            balanceDTO.setVinculables(listaVinculables);
            balanceDTO.setFaltantePorVincular(balance.faltantePorCubrir());

            resultadoFinalBalances.add(balanceDTO);
        }
        return resultadoFinalBalances;
    }

}
