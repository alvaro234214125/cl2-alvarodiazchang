package pe.edu.cibertec.cl2_alvaro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageId;
    private String name;
    private Date lastUpdate;

    @OneToMany(mappedBy = "language")
    private List<Film> films;

    public Language(Integer integer, String s, Date date) {
    }
}
