package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Certificado;

import java.util.List;
import java.util.Optional;

public interface ICertificadoService {
    public List<Certificado> list();
    public Certificado insert(Certificado c);
    public Optional<Certificado> listId(Long id);
    public Certificado update(Certificado c);
    public void delete(Long id);
}
