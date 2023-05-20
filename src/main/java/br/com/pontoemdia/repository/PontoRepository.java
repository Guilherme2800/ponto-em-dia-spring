package br.com.pontoemdia.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pontoemdia.model.Ponto;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Long>{
	
	@Query(value = "SELECT * FROM ponto WHERE user_id = :userId AND data = :data", nativeQuery = true)
	public Ponto findByUserIdAndDate(Long userId, String data);

	@Query(value = "SELECT * FROM ponto WHERE user_id = :userId AND data >= :dataInicial AND data <= :dataFinal", nativeQuery = true)
	public List<Ponto> findByUserIdAndStartDateAndEndDate(Long userId, String dataInicial, String dataFinal);
	
	@Query(value = "SELECT * FROM ponto WHERE user_id = :userId", nativeQuery = true)
	public List<Ponto> findByUserId(Long userId);
	
	@Query(value = "SELECT * FROM ponto WHERE user_id = :userId and data = :date", nativeQuery = true)
	public Ponto buscarPontoDiaAnteriorDoUsuario(Long userId, String date);

}
