package io.github.JhonDias98.rest.controller;

import io.github.JhonDias98.domain.entity.Cliente;
import io.github.JhonDias98.domain.repository.Clientes;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("API Clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public Cliente getClienteById(@PathVariable @ApiParam("ID do cliente") Integer id) {
        return clientes.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente) {
        return clientes.save(cliente);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deletar cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public void delete(@PathVariable @ApiParam("ID do cliente") Integer id) {
        clientes.findById(id)
                .map(cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public void update(@PathVariable @ApiParam("ID do cliente") Integer id,
                       @RequestBody @Valid Cliente cliente) {
        clientes
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));;
    }

    @GetMapping
    @ApiOperation("Obter detalhes todos os cliente")
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Cliente> lista = clientes.findAll(example);
        return clientes.findAll(example);
    }
}