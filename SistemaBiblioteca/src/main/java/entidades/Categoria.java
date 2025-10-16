package entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id") // Basar igualdad solo en el ID
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categorias")
    private Set<Libro> libros = new HashSet<>();

    // Constructor personalizado
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}