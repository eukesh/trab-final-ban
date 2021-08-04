package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EnderecoDAO {

    private static EnderecoDAO instance = null;


    private final PreparedStatement selectNewId;
    private final PreparedStatement insert;
    private final PreparedStatement delete;
    private final PreparedStatement selectAll;

    public static EnderecoDAO getInstace() throws ClassNotFoundException, SQLException {
        if(instance == null){
            instance = new EnderecoDAO();
        }
        return instance;
    }

    private EnderecoDAO() throws ClassNotFoundException, SQLException {
        Connection conexao = ConectionBd.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_animal')");
        insert = conexao.prepareStatement("insert into animal values(?,?,?)");
        select = conexao.prepareStatement("select * from animal where id = ?");
        delete = conexao.prepareStatement("delete from animal where id=?");
        selectAll = conexao.prepareStatement("select * from animal");
    }

    private int selectNewId()  {
        try{
            ResultSet rs = selectNewId.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException e){
            throw new SelectException("Erro ao buscar novo id na tabela contato");
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

    public List<Animal> getAll() throws SelectException, SQLException {
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
