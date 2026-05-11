package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.UsuarioAuditoria;
import java.util.List;
import java.util.Optional;

public interface IUsuarioAuditoriaService {
    public List<UsuarioAuditoria> list();
    public UsuarioAuditoria insert(UsuarioAuditoria u);
    public Optional<UsuarioAuditoria> listId(int id);
    public UsuarioAuditoria update(UsuarioAuditoria u);
    public void delete(int id);
}