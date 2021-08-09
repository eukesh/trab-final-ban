package controller;

import database.*;
import model.Animal;
import model.DonoCliente;
import model.Endereco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Controller {
    private static Controller instance = null;

    private static AnimalDAO animalDao;
    private static DonoClienteDAO donoDao;
    private static EnderecoDAO enderecoDao;
    private static EDonoDAO eDonoDAO;

    public static Controller getInstance() throws SQLException, ClassNotFoundException {
        if(instance == null) {
            instance = new Controller();
            ConectionBd.setSenha("toor");
            animalDao = AnimalDAO.getInstace();
            donoDao = DonoClienteDAO.getInstace();
            enderecoDao = EnderecoDAO.getInstace();
            eDonoDAO = EDonoDAO.getInstace();
        }
        return instance;
    }
    public void dono(){}

    public void insertAnimal(Animal animal) throws SQLException {
        animalDao.insert(animal);
    }

    public void insertEndereco(Endereco endereco) throws SQLException {
        enderecoDao.insert(endereco);
    }

    public void insertDono(DonoCliente dono) throws SQLException {
        donoDao.insert(dono);
    }

    public void insertDonoAnimal(DonoCliente dono,Animal animal) throws SQLException {
        eDonoDAO.insert(dono,animal);
    }

    public void deleteAnimal(Animal animal) throws SQLException {
        animalDao.delete(animal);
    }

    public void deleteEndereco(Endereco endereco) throws SQLException {
        enderecoDao.delete(endereco);
    }

    public void deleteDono(DonoCliente dono) throws SQLException {
        donoDao.delete(dono);
    }

    public List<Animal> getAllAnimal() throws SQLException {
        return animalDao.getAll();
    }

    public Map<DonoCliente,List<Animal>> getAllAnimalWithDono() throws SQLException {
        return animalDao.getAllWithDono();
    }

    public List<DonoCliente> getAllDono() throws SQLException {
        return donoDao.getAll();
    }

    public List<Endereco> getAllEndereco() throws SQLException {
        return enderecoDao.getAll();
    }

    public DonoCliente selectDono(int idDono) throws SQLException {
        return donoDao.select(idDono);
    }

    public Animal selectAnimal(int id) throws SQLException {
        return animalDao.select(id);
    }

    public Endereco selectEndereco(int id) throws SQLException{
        return enderecoDao.selectId(id);
    }

}
