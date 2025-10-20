package ni.edu.uam.jpa_relaciones.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
        private static final EntityManagerFactory EMF =
                Persistence.createEntityManagerFactory("uam");

        public static EntityManager getEntityManager() {
            return EMF.createEntityManager();
        }

        public static void close() {
            if (EMF != null && EMF.isOpen()) {
                EMF.close();
            }
        }
}
