package transcesar.service;

import transcesar.dao.RutaDao;
import transcesar.model.Ruta;

import java.util.List;

public class RutaService {

    private RutaDao rutaDao = new RutaDao();

    public void registrarRuta(String codigo, String origen, String destino,
                              double distancia, int tiempo) {
        Ruta ruta = new Ruta(codigo, origen, destino, distancia, tiempo);
        rutaDao.guardarRuta(ruta);
        System.out.println("Ruta registrada exitosamente: " + codigo);
    }

    public List<Ruta> listarRutas() {
        return rutaDao.cargarRutas();
    }

    public Ruta buscarPorCodigo(String codigo) {
        return rutaDao.cargarRutas().stream()
                .filter(r -> r.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }
}
