package database;

import model.Animal;
import model.Endereco;

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
    private final PreparedStatement select;
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
        selectNewId = conexao.prepareStatement("select nextval('id_endereco')");
        insert = conexao.prepareStatement("insert into endereco values(?,?,?,?,?,?,?)");
        select = conexao.prepareStatement("select * from endereco where id = ?");
        delete = conexao.prepareStatement("delete from endereco where id=?");
        selectAll = conexao.prepareStatement("select * from endereco");
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

    public void insert(Endereco endereco) throws SQLException {
        try{
            insert.setInt(1,selectNewId());
            insert.setString(2,endereco.getEstado());
            insert.setString(3,endereco.getRua());
            insert.setInt(4,endereco.getNumero());
            insert.setString(5,endereco.getBairro());
            insert.setString(6,endereco.getCidade());
            insert.setInt(7,endereco.getCep());
            insert.executeUpdate();

        }catch (SQLException e){
            throw new SQLException("Erro ao inserir endereco");
        }
    }

    public void delete(Endereco endereco) throws SQLException {
        try {
            delete.setInt(1,endereco.getId());
            delete.executeUpdate();
        }catch (SQLException e){
            throw new SQLException("Erro ao deletar endereco");
        }
    }

    public Endereco selectId(int  idEndereco) throws SQLException{
        try{
            select.setInt(1,idEndereco);
            ResultSet rs = select.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String estado = rs.getString(2);
                String rua = rs.getString(3);
                int numero = rs.getInt(4);
                String bairro = rs.getString(5);
                String cidade = rs.getString(6);
                int cep = rs.getInt(7);
                return new Endereco(id,estado,rua,numero,bairro,cidade,cep);
            }
        }catch (SQLException e){
            throw new SQLException("Erro ao buscar Endereco");
        }
        return null;
    }

    public List<Endereco> getAll() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        try{

            ResultSet rs = selectAll.executeQuery();


            while (rs.next()){
                int id = rs.getInt(1);
                String estado = rs.getString(2);
                String rua = rs.getString(3);
                int numero = rs.getInt(4);
                String bairro = rs.getString(5);
                String cidade = rs.getString(6);
                int cep = rs.getInt(7);
                enderecos.add(new Endereco(id,estado,rua,numero,bairro,cidade,cep));

            }
            return enderecos;

        }catch (SQLException e){
            throw new SQLException("Erro ao buscar animais");
        }

    }
}
