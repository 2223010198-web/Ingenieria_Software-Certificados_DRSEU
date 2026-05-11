package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Certificado;
import pe.edu.untels.certificadosdrsu.repositories.ICertificadoRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ICertificadoService;

import java.util.List;
import java.util.Optional;

@Service
public class CertificadoServiceImplement implements ICertificadoService {
    @Autowired
    private ICertificadoRepository certificadoRepository;

    @Override
    public List<Certificado> list() {
        return certificadoRepository.findAll();
    }

    @Override
    public Certificado insert(Certificado c) {
        return certificadoRepository.save(c);
    }

    @Override
    public Optional<Certificado> listId(Long id) {
        return certificadoRepository.findById(id);
    }

    @Override
    public Certificado update(Certificado c) {
        return certificadoRepository.save(c);
    }

    @Override
    public void delete(Long id) {
        certificadoRepository.deleteById(id);
    }
}
