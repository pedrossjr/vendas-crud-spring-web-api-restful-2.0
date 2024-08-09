package io.github.pedrossjrfps.domain.repository;

import io.github.pedrossjrfps.domain.entity.ItemPedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
