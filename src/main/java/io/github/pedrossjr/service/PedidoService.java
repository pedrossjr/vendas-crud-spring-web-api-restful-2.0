package io.github.pedrossjr.service;

import io.github.pedrossjr.domain.entity.Pedido;
import io.github.pedrossjr.domain.enums.StatusPedido;
import io.github.pedrossjr.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
