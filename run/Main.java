package run; //

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import entidades.Autor;
import entidades.Categoria;
import entidades.Libro;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Usa el nombre de la unidad de persistencia de tu archivo persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");
        EntityManager em = emf.createEntityManager();

        try {
            // --- INSERCIÓN DE DATOS ---
            em.getTransaction().begin();

            // Insertar Autores
            Autor autor1 = new Autor("Gabriel García Márquez", "Colombiano", LocalDate.of(1927, 3, 6));
            Autor autor2 = new Autor("Isabel Allende", "Chilena", LocalDate.of(1942, 8, 2));
            em.persist(autor1);
            em.persist(autor2);

            // Insertar Categorías
            Categoria catFiccion = new Categoria("Ficción");
            Categoria catRealismo = new Categoria("Realismo Mágico");
            em.persist(catFiccion);
            em.persist(catRealismo);

            // Insertar Libros y relacionarlos
            Libro libro1 = new Libro("Cien años de soledad", 1967, autor1);
            libro1.addCategoria(catFiccion);
            libro1.addCategoria(catRealismo);
            em.persist(libro1);

            Libro libro2 = new Libro("El amor en los tiempos del cólera", 1985, autor1);
            libro2.addCategoria(catFiccion);
            em.persist(libro2);

            Libro libro3 = new Libro("La casa de los espíritus", 1982, autor2);
            libro3.addCategoria(catFiccion);
            libro3.addCategoria(catRealismo);
            em.persist(libro3);

            em.getTransaction().commit();
            System.out.println("\n ¡Datos insertados correctamente en la base de datos PostgreSQL!\n");

            // --- CONSULTA DE DATOS ---
            System.out.println("---  Listado de Libros en la Biblioteca ---");

            TypedQuery<Libro> query = em.createQuery(
                    "SELECT DISTINCT l FROM Libro l JOIN FETCH l.autor JOIN FETCH l.categorias", Libro.class);
            List<Libro> libros = query.getResultList();

            for (Libro libro : libros) {
                System.out.println("📘 Título: " + libro.getTitulo() + " (" + libro.getAnioPublicacion() + ")");
                System.out.println("   ✍️ Autor: " + libro.getAutor().getNombre());
                System.out.print("   🏷️ Categorías: ");
                libro.getCategorias().forEach(cat -> System.out.print("[" + cat.getNombre() + "] "));
                System.out.println("\n-------------------------------------------------");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}