package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;
import pe.edu.untels.certificadosdrsu.repositories.IEnvioCorreoRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IEnvioCorreoService;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioCorreoServiceImplement implements IEnvioCorreoService {
    @Autowired
    private IEnvioCorreoRepository envioCorreoRepository;

    @Override
    public List<EnvioCorreo> list() {
        return envioCorreoRepository.findAll();
    }

    @Override
    public EnvioCorreo insert(EnvioCorreo e) {
        return envioCorreoRepository.save(e);
    }

    @Override
    public Optional<EnvioCorreo> listId(int id) {
        return envioCorreoRepository.findById(id);
    }

    @Override
    public EnvioCorreo update(EnvioCorreo e) {
        return envioCorreoRepository.save(e);
    }

    @Override
    public void delete(int id) {
        envioCorreoRepository.deleteById(id);
    }
}
