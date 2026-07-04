package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;
import pe.edu.untels.certificadosdrsu.repositories.IPlantillaPdfRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IPlantillaPdfService;

import java.util.List;
import java.util.Optional;

@Service
public class PlantillaPdfServiceImplement implements IPlantillaPdfService {

    @Autowired
    private IPlantillaPdfRepository repository;

    @Override
    public List<PlantillaPdf> list() {
        return repository.findAll();
    }

    @Override
    public PlantillaPdf insert(PlantillaPdf p) {
        return repository.save(p);
    }

    @Override
    public Optional<PlantillaPdf> listId(Long id) {
        return repository.findById(id);
    }

    @Override
    public PlantillaPdf update(PlantillaPdf p) {
        return repository.save(p);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}