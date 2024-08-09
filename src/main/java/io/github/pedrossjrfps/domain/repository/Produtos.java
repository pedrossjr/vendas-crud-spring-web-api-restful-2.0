package io.github.pedrossjrfps.domain.repository;

import io.github.pedrossjrfps.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
