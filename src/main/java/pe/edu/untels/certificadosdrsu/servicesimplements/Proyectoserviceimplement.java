package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.repositories.Iproyectorepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.Iproyectoservice;

import java.util.List;

@Service
public class Proyectoserviceimplement implements Iproyectoservice {

    @Autowired
    private Iproyectorepository proyectoRepository;

    @Override
    public List<Proyecto> list() {
        return proyectoRepository.findAll();
    }

}
