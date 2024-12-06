package pe.edu.cibertec.cl2_alvaro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.cl2_alvaro.dto.FilmDetailDto;
import pe.edu.cibertec.cl2_alvaro.dto.FilmDto;
import pe.edu.cibertec.cl2_alvaro.entity.Film;
import pe.edu.cibertec.cl2_alvaro.entity.Language;
import pe.edu.cibertec.cl2_alvaro.repository.FilmRepository;
import pe.edu.cibertec.cl2_alvaro.repository.LanguageRepository;
import pe.edu.cibertec.cl2_alvaro.service.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    @Cacheable("films")
    public List<FilmDto> findAllFilms() {
        List<FilmDto> films = new ArrayList<>();
        Iterable<Film> iterable = filmRepository.findAllBy();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });
        return films;
    }

    @Override
    public FilmDetailDto findFilmById(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> new FilmDetailDto(film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage().getLanguageId(),
                film.getLanguage().getName(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate())
        ).orElse(null);
    }

    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(film -> {
            film.setTitle(filmDetailDto.title());
            film.setDescription(filmDetailDto.description());
            film.setReleaseYear(filmDetailDto.releaseYear());
            film.setRentalDuration(filmDetailDto.rentalDuration());
            film.setRentalRate(filmDetailDto.rentalRate());
            film.setLength(filmDetailDto.length());
            film.setReplacementCost(filmDetailDto.replacementCost());
            film.setRating(filmDetailDto.rating());
            film.setSpecialFeatures(filmDetailDto.specialFeatures());
            film.setLastUpdate(new Date());
            filmRepository.save(film);
            return true;
        }).orElse(false);
    }

    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean deleteFilmById(int id) {
        try {
            filmRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar película: " + e.getMessage());
            return false;
        }
    }

    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean createFilm(FilmDetailDto filmDetailDto) {
        try {
            Optional<Language> optionalLanguage = languageRepository.findById(filmDetailDto.languageId());
            if (optionalLanguage.isEmpty()) {
                throw new Exception("Language not found for ID: " + filmDetailDto.languageId());
            }
            Language language = optionalLanguage.get();

            Film film = new Film();
            film.setTitle(filmDetailDto.title());
            film.setDescription(filmDetailDto.description());
            film.setReleaseYear(filmDetailDto.releaseYear());
            film.setRentalDuration(filmDetailDto.rentalDuration());
            film.setRentalRate(filmDetailDto.rentalRate());
            film.setLength(filmDetailDto.length());
            film.setReplacementCost(filmDetailDto.replacementCost());
            film.setRating(filmDetailDto.rating());
            film.setSpecialFeatures(filmDetailDto.specialFeatures());
            film.setLastUpdate(new Date());
            film.setLanguage(language);
            filmRepository.save(film);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear película: " + e.getMessage());
            return false;
        }
    }
}
