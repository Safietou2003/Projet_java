package newstart.repository.repositoryList;


import java.util.ArrayList;
import java.util.List;

import newstart.entities.Client;
import newstart.entities.Dette;
import newstart.repository.implementation.RepositoryDette;

public class DetteRepository extends RepositoryImplList<Dette> implements RepositoryDette{

    @Override
    public List<Dette> findFiltre(){
        List<Dette> dettes =new ArrayList<>();
        for (Dette dette : dettes) {
            if(dette.getMontantRestant()!=0){
                dettes.add(dette);
            }
        }
        return dettes;
    }

    @Override
    public Client getByClient(int id) {
        for(Dette dette:datas){
            if(dette.getClient().getId() == id){
                return dette.getClient();
            }
        }
        return null;
    }

    @Override
    public List<Dette> getDettesByClientId(int clientId) {
        List<Dette> dettesByClient = new ArrayList<>();
        
        for (Dette dette : datas) {
            if (dette.getClient().getId() == clientId) {
                dettesByClient.add(dette);
            }
        }
        return dettesByClient;
    }

    @Override
    public List<Dette> getDettesEnCoursByClientId(int clientId) {
        List<Dette> dettesEnCours = new ArrayList<>();
        if(clientId == 0){
            for (Dette dette : datas) {
                if("En cours".equals(dette.getValidation())){
                    dettesEnCours.add(dette);
                }
            }
        }else{
            for (Dette dette : getDettesByClientId(clientId)) {
                if("En cours".equals(dette.getValidation())){
                    dettesEnCours.add(dette);
                }
            }
        }
        return dettesEnCours;
    }

    @Override
    public List<Dette> getDettesNonSoldeesByClientId(int clientId) {
        List<Dette> dettesNonSoldees = new ArrayList<>();
        for (Dette dette : getDettesByClientId(clientId)) {
            if (Boolean.FALSE.equals(dette.getSoldation())) {
                dettesNonSoldees.add(dette);
            }
        }
        return dettesNonSoldees;
    }

    @Override
    public void updateValidation(int id, String etat) {
        for (Dette dette : datas) {
            if (dette.getId() == id) {
                dette.setValidation(etat);
            }
        }
    }
    
}
