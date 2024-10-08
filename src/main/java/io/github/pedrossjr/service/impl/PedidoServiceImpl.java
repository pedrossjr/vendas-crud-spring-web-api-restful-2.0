package io.github.pedrossjr.service.impl;

import io.github.pedrossjr.domain.entity.Cliente;
import io.github.pedrossjr.domain.entity.ItemPedido;
import io.github.pedrossjr.domain.entity.Pedido;
import io.github.pedrossjr.domain.entity.Produto;
import io.github.pedrossjr.domain.enums.StatusPedido;
import io.github.pedrossjr.domain.repository.Clientes;
import io.github.pedrossjr.domain.repository.ItemsPedido;
import io.github.pedrossjr.domain.repository.Pedidos;
import io.github.pedrossjr.domain.repository.Produtos;
import io.github.pedrossjr.exception.PedidoNaoEncontradoException;
import io.github.pedrossjr.exception.RegraNegocioException;
import io.github.pedrossjr.rest.dto.ItemPedidoDTO;
import io.github.pedrossjr.rest.dto.PedidoDTO;
import io.github.pedrossjr.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRspository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar( PedidoDTO dto ) {
        Integer idCliente =  dto.getCliente();
        Cliente cliente = clientesRspository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() ->
                        new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items ) {
        if(items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException("Código de produto inválido: " + idProduto)
                            );

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
