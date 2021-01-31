package io.github.JhonDias98.service;

import io.github.JhonDias98.domain.entity.Pedido;
import io.github.JhonDias98.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
