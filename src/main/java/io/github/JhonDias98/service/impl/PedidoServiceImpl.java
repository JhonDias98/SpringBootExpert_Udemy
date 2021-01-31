package io.github.JhonDias98.service.impl;

import io.github.JhonDias98.domain.entity.Cliente;
import io.github.JhonDias98.domain.entity.ItemPedido;
import io.github.JhonDias98.domain.entity.Pedido;
import io.github.JhonDias98.domain.entity.Produto;
import io.github.JhonDias98.domain.repository.Clientes;
import io.github.JhonDias98.domain.repository.ItemsPedido;
import io.github.JhonDias98.domain.repository.Pedidos;
import io.github.JhonDias98.domain.repository.Produtos;
import io.github.JhonDias98.exception.RegraNegocioException;
import io.github.JhonDias98.rest.dto.ItemPedidoDTO;
import io.github.JhonDias98.rest.dto.PedidoDTO;
import io.github.JhonDias98.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {

        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código do cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itensPedidos = converterItens(pedido, dto.getItems());
        repository.save(pedido);
        itemPedidoRepository.saveAll(itensPedidos);
        pedido.setItens(itensPedidos);

        return pedido;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if(itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return itens.stream().map(dto -> {
            Integer idProdto = dto.getProduto();
            Produto produto = produtosRepository
                    .findById(idProdto)
                    .orElseThrow(() -> new RegraNegocioException(
                            "Código do produto inválido:" + idProdto
                    ));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);

            return itemPedido;
        }).collect(Collectors.toList());
    }
}
