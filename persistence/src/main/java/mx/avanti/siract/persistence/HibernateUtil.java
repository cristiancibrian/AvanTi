package mx.avanti.siract.persistence;

import org.hibernate.HibernateException;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 *
 * @author @version ThreadSafe checked September 29-2015
 */
public class HibernateUtil {

    private static final SessionFactory SESSIONFACTORY = buildSessionFactory();
    private static final ThreadLocal<Session> THREADSESSION = new ThreadLocal<>();
    private static final ThreadLocal<Transaction> THREADTRANSACCION = new ThreadLocal<>();

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        try {
            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println(ex);
            StandardServiceRegistryBuilder.destroy(registry);

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSIONFACTORY;
    }

    public static Session getSession() {
        Session s = (Session) THREADSESSION.get();

        try {
            if (s == null) {
                s = SESSIONFACTORY.openSession();
                THREADSESSION.set(s);
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        }

        return s;
    }

    public static void beginTransaccion() {
        Transaction tx = (Transaction) THREADTRANSACCION.get();

        try {
            if (tx == null) {
                tx = getSession().beginTransaction();
                THREADTRANSACCION.set(tx);
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        }
    }

    public static void closeSession() {
        // TODO Buscar solucion a error de Hikari al cerrar sesión.
        try {
            Session s = (Session) THREADSESSION.get();

            if (THREADTRANSACCION.get() != null) {
                THREADTRANSACCION.set(null);
            }

            THREADSESSION.set(null);

            if (s != null && s.isOpen()) {
                s.close();
            }
        } catch (StaleStateException ex) { //TEMPORAL_FIX:Se agrego este catch para evitar error producido por el manejo del Hikari
            System.err.println(ex);
        } catch (HibernateException ex) {
            System.err.println(ex);
        }
    }

    public static void beingTransaccion() {
        Transaction tx = (Transaction) THREADTRANSACCION.get();
        try {
            if (tx == null) {
                tx = getSession().beginTransaction();
                THREADTRANSACCION.set(tx);
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public static void commitTransaction() {
        Transaction tx = (Transaction) THREADTRANSACCION.get();

        try {
            if (tx != null && !tx.getStatus().isOneOf(TransactionStatus.COMMITTED) && !tx.getStatus().isOneOf(TransactionStatus.ROLLED_BACK)) {
                tx.commit();
                THREADTRANSACCION.set(null);
            }
        } catch (ResourceClosedException ex) {
            System.err.println(ex);
        } catch (HibernateException ex) {
            rollbackTransaction();
            System.err.println(ex);
        }
    }

    public static void rollbackTransaction() {
        Transaction tx = (Transaction) THREADTRANSACCION.get();

        try {
            THREADTRANSACCION.set(null);

            if (tx != null && !tx.getStatus().isOneOf(TransactionStatus.COMMITTED) && !tx.getStatus().isOneOf(TransactionStatus.ROLLED_BACK)) {
                tx.rollback();
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        }
    }

}
