package database;

import model.Animal;
import model.DonoCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class AnimalDAO {

    private static AnimalDAO instance = null;
    private static EnderecoDAO enderecoDao = null;


    private final PreparedStatement selectNewId;
    private final PreparedStatement insert;
    private final PreparedStatement select;
    private final PreparedStatement delete;
    private final PreparedStatement selectAll;
    private final PreparedStatement selectAnimalDono;

    public static AnimalDAO getInstace() throws ClassNotFoundException, SQLException {
        if(instance == null){
            instance = new AnimalDAO();
            enderecoDao = EnderecoDAO.getInstace();
        }
        return instance;
    }

    private AnimalDAO() throws ClassNotFoundException, SQLException {
        Connection conexao = ConectionBd.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_animal')");
        insert = conexao.prepareStatement("insert into animal values(?,?,?)");
        select = conexao.prepareStatement("select * from animal where id = ?");
        delete = conexao.prepareStatement("delete from animal where id=?");
        selectAll = conexao.prepareStatement("select * from animal");
        selectAnimalDono = conexao.prepareStatement("select * from animal a join (select * from edono join clientedono c on edono.cpf_dono = c.cpf) as p2 on a.id = p2.id_animal");
    }

    private int selectNewId() throws SQLException {
        try{
            ResultSet rs = selectNewId.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException e){
            throw new SQLException("Erro ao buscar novo id na tabela contato");
        }
        return 0;
    }

    public void insert(Animal animal) throws SQLException {
        try{
            insert.setInt(1,selectNewId());
            insert.setString(2,animal.getNome());
            insert.setString(3,animal.getDescricao());
            insert.executeUpdate();

        }catch (SQLException e){
            throw new SQLException("Erro ao inserir animal");
        }
    }

    public void delete(Animal animal) throws SQLException {
        try {
            delete.setInt(1,animal.getId());
            delete.executeUpdate();
        }catch (SQLException e){
            throw new SQLException("Erro ao deletar animal");
        }
    }

    public Animal select(int id) throws SQLException {
        try{
            select.setInt(1,id);
            ResultSet rs = select.executeQuery();
            while (rs.next()){
                int idAnimal = rs.getInt(1);
                String nome = rs.getString(2);
                String descricao = rs.getString(3);
                return new Animal(idAnimal,nome,descricao);
            }
        }catch (SQLException e){
            throw new SQLException("Erro ao buscar Animal");
        }
        return null;
    }

    public Map<DonoCliente,List<Animal>> getAllWithDono() throws SQLException {
        Map<DonoCliente,List<Animal>> animaisdono = new HashMap<>();
        try{

            ResultSet rs = selectAnimalDono.executeQuery();
            while (rs.next()){
                List<Animal> temp = new ArrayList<>();
                int idAnimal = rs.getInt(1);
                String nomeAnimal = rs.getString(2);
                String descricaoAnimal = rs.getString(3);
                int cpfDono = rs.getInt(7);
                String nomeDono = rs.getString(8);
                int telefone = rs.getInt(9);
                int idEndereco = rs.getInt(10);

                Animal animal = new Animal(idAnimal,nomeAnimal,descricaoAnimal);
                DonoCliente dono = new DonoCliente(cpfDono,nomeDono,telefone,enderecoDao.selectId(idEndereco));

                if(animaisdono.containsKey(dono)){
                    animaisdono.get(dono).add(animal);
                }else{
                    temp.add(animal);
                    animaisdono.put(dono,temp);
                }
            }
            return animaisdono;

        }catch (SQLException e){
            throw new SQLException("Erro ao buscar animais");
        }
    }

    public List<Animal> getAll() throws  SQLException {
        List<Animal> animais = new ArrayList<>();
        try{

            ResultSet rs = selectAll.executeQuery();


            while (rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String descricao = rs.getString(3);
                animais.add(new Animal(id,nome,descricao));

            }
            return animais;

        }catch (SQLException e){
            throw new SQLException("Erro ao buscar animais");
        }

    }
}
