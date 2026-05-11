package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;
import java.util.List;
import java.util.Optional;

public interface IPlantillaPdfService {
    public List<PlantillaPdf> list();
    public PlantillaPdf insert(PlantillaPdf p);
    public Optional<PlantillaPdf> listId(int id);
    public PlantillaPdf update(PlantillaPdf p);
    public void delete(int id);
}