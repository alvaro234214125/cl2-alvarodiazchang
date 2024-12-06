package pe.edu.cibertec.cl2_alvaro.service;

import pe.edu.cibertec.cl2_alvaro.dto.FilmDetailDto;
import pe.edu.cibertec.cl2_alvaro.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();

    FilmDetailDto findFilmById(int id);
    Boolean createFilm(FilmDetailDto filmDetailDto);
    Boolean updateFilm(FilmDetailDto filmDetailDto);
    Boolean deleteFilmById(int id);
}
