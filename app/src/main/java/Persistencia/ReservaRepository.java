package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.Reserva;
import com.j256.ormlite.support.ConnectionSource;

public class ReservaRepository extends Repository<Reserva> {


    public ReservaRepository(ConnectionSource c) {
        init(Reserva.class, c);
    }
}
