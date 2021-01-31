package io.github.JhonDias98.service.impl;

import io.github.JhonDias98.domain.repository.Pedidos;
import io.github.JhonDias98.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService{

    private Pedidos repository;

    public PedidoServiceImpl(Pedidos repository) {
        this.repository = repository;
    }
}
