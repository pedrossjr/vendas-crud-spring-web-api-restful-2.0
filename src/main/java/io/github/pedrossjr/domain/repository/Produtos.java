package io.github.pedrossjr.domain.repository;

import io.github.pedrossjr.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
