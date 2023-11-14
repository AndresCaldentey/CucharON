package Persistencia;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class Repository<T> {
    private static ConnectionSource connectionSource;
    private Dao<T, Integer> dao;
    private T clase;
    private List<T> listaDeClase;

    public void init(Class myclass, ConnectionSource c) {
        try {
            connectionSource = c;
            dao = DaoManager.createDao(connectionSource, myclass);

        } catch (Exception e) {
            System.out.println(e);

        }
    }
        public Dao<T, Integer> getDao(){
            return dao;
        }
        public ConnectionSource getConnectionSource() {
            return connectionSource;
        }

        public void guardar(T entity) {
            Thread hilo = new Thread(() -> {
                try {
                    this.getDao().create(entity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();
        }

        public void guardar2(T entity){
            Thread hilo = new Thread(() -> {
                try {
                    this.getDao().create(entity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();

            //Esperar al hilo
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public List<T> obtenerTodos(){
            listaDeClase = new ArrayList<>();
            Thread hilo = new Thread(() -> {
                try {
                    listaDeClase = this.getDao().queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();

            //Esperar al hilo
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return listaDeClase;
        }

        public void delete(int id){
            Thread hilo = new Thread(() -> {
                try {
                    this.getDao().deleteById(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();
        }

        public T obtener(int id){
            clase = null;
            Thread hilo = new Thread(() -> {
                try {
                    clase = this.getDao().queryForId(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();

            //Esperar al hilo
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return clase;
        }

        public void actualizar(T t){
            Thread hilo = new Thread(() -> {
                try {
                    this.getDao().update(t);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();
        }

    }
