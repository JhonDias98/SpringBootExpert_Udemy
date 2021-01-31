package io.github.JhonDias98.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public  PedidoNaoEncontradoException() {
        super("Pedido não encontrado");
    }
}
