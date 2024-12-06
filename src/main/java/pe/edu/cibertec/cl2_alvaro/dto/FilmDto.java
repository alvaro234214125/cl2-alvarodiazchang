package pe.edu.cibertec.cl2_alvaro.dto;

public record FilmDto(Integer filmId,
                      String title,
                      String language,
                      Integer rentalDuration,
                      Double rentalRate) {

}
