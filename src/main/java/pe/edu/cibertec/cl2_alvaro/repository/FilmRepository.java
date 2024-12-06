package pe.edu.cibertec.cl2_alvaro.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.cl2_alvaro.entity.Film;

public interface FilmRepository extends CrudRepository<Film, Integer> {

    @Cacheable("films")
    Iterable<Film> findAllBy();

    @Override
    @CacheEvict(value = "films", allEntries = true)
    <S extends Film> S save(S entity);

    @Override
    @CacheEvict(value = "films", allEntries = true)
    void deleteById(Integer id);
}
