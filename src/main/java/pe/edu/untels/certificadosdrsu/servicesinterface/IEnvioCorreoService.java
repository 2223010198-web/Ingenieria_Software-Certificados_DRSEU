package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;

import java.util.List;
import java.util.Optional;

public interface IEnvioCorreoService {
    public List<EnvioCorreo> list();
    public EnvioCorreo insert(EnvioCorreo e);
    public Optional<EnvioCorreo> listId(int id);
    public EnvioCorreo update(EnvioCorreo e);
    public void delete(int id);
}
