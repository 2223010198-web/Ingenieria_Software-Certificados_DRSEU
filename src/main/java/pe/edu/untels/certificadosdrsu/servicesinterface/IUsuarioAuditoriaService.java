package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.UsuarioAuditoria;
import java.util.List;
import java.util.Optional;

public interface IUsuarioAuditoriaService {
    public List<UsuarioAuditoria> list();
    public UsuarioAuditoria insert(UsuarioAuditoria u);
    public Optional<UsuarioAuditoria> listId(Long id);
    public UsuarioAuditoria update(UsuarioAuditoria u);
    public void delete(Long id);
}