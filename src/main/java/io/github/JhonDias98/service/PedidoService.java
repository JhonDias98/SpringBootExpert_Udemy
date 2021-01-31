package io.github.JhonDias98.service;

import io.github.JhonDias98.domain.entity.Pedido;
import io.github.JhonDias98.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
