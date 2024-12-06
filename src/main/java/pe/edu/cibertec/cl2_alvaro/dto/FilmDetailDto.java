package pe.edu.cibertec.cl2_alvaro.dto;

import java.util.Date;

public record FilmDetailDto(Integer filmId,
                            String title,
                            String description,
                            Integer releaseYear,
                            Integer languageId,
                            String languageName,
                            Integer rentalDuration,
                            Double rentalRate,
                            Integer length,
                            Double replacementCost,
                            String rating,
                            String specialFeatures,
                            Date lastUpdate) {

}
