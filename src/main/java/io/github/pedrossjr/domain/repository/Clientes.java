package io.github.pedrossjr.domain.repository;

import io.github.pedrossjr.domain.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    // List<Cliente> encontrarPorNome(String nome);

    // @Query(value = " select c from Cliente c where c.nome like :nome ")
    // List<Cliente> encontrarPorNome( @Param("nome") String nome);

    @Query(value = " select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNome( @Param("nome") String nome);

    // void deleteByNome(String nome);

    @Query(" delete from Cliente c where c.nome = :nome ")
    @Modifying //Utilizado somente no update ou delete
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos( @Param("id") Integer id );



}
