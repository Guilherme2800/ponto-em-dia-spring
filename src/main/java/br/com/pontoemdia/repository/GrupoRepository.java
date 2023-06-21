package br.com.pontoemdia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pontoemdia.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

}
