package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.dtos.TipoCertificadoDTO;
import pe.edu.untels.certificadosdrsu.dtos.TipoCertificadoInsertDTO;

import java.util.List;

public interface ITipoCertificadoService {
    List<TipoCertificadoDTO> listarActivos();
    TipoCertificadoDTO crear(TipoCertificadoInsertDTO dto);
    TipoCertificadoDTO actualizar(Long id, TipoCertificadoInsertDTO dto);
    void desactivar(Long id);
}
