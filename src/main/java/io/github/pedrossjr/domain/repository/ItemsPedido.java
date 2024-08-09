package io.github.pedrossjr.domain.repository;

import io.github.pedrossjr.domain.entity.ItemPedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
