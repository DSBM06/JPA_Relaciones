package ni.edu.uam.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
public class Autor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 60)
    @Column(length = 60)
    private String nacionalidad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    // Un autor tiene muchos libros (lado inverso controlado por Libro.autor)
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.NONE)
    private List<Libro> libros = new ArrayList<>();
}package ni.edu.uam.entities;

import jakarta.persistence.*;
        import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_categoria_nombre", columnNames = {"nombre"})
        })
@Data
@NoArgsConstructor
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 2, max = 60)
    @Column(nullable = false, length = 60)
    private String nombre;

    // Lado inverso del ManyToMany (dueño = Libro.categorias)
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    private Set<Libro> libros = new HashSet<>();
}
