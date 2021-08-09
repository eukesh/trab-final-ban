package database;

import model.Animal;
import model.DonoCliente;
import model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DonoClienteDAO {

    private static DonoClienteDAO instance = null;
    private static EnderecoDAO endereco = null;

    private final PreparedStatement insert;
    private final PreparedStatement select;
    private final PreparedStatement delete;
    private final PreparedStatement selectAll;

    public static DonoClienteDAO getInstace() throws ClassNotFoundException, SQLException {
        if(instance == null){
            instance = new DonoClienteDAO();
            endereco = EnderecoDAO.getInstace();
        }
        return instance;
    }

    private DonoClienteDAO() throws ClassNotFoundException, SQLException {
        Connection conexao = ConectionBd.getConexao();
        insert = conexao.prepareStatement("insert into clientedono values(?,?,?,?)");
        select = conexao.prepareStatement("select * from clientedono where cpf=?");
        delete = conexao.prepareStatement("delete from clientedono where cpf=?");
        selectAll = conexao.prepareStatement("select cpf,nome,telefone,endereco.id from\n" + // juncao entre clientedono e endereco
                "clientedono join endereco\n" +
                "on clientedono.id_endereco = endereco.id");
    }

    public void insert(DonoCliente dono) throws SQLException {
        try{
            insert.setInt(1,dono.getCpf());
            insert.setString(2,dono.getNome());
            insert.setInt(3,dono.getTelefone());
            insert.setInt(4,dono.getEndereco().getId());
            insert.executeUpdate();

        }catch (SQLException e){
            throw new SQLException("Erro ao inserir Dono");
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConectionBd.setSenha("toor");
        DonoClienteDAO a = DonoClienteDAO.getInstace();
        a.insert(new DonoCliente(12345456,"Carlinhios",
                1234, endereco.selectId(7)));
    }

    public DonoCliente select(int idDono) throws SQLException {
        try{
            select.setInt(1,idDono);
            ResultSet rs = select.executeQuery();

            while(rs.next()){
                int cpf = rs.getInt(1);
                String nome = rs.getString(2);
                int telefone = rs.getInt(3);
                int idEndereco = rs.getInt(4);
                return new DonoCliente(cpf,nome,telefone,endereco.selectId(idEndereco));
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao selecionar dono");
        }
        return null;
    }

    public void delete(DonoCliente dono) throws SQLException {
        try {
            delete.setInt(1,dono.getCpf());
            delete.executeUpdate();
        }catch (SQLException e){
            throw new SQLException("Erro ao deletar animal");
        }
    }

    public List<DonoCliente> getAll() throws  SQLException {
        List<DonoCliente> donos = new ArrayList<>();
        try{

            ResultSet rs = selectAll.executeQuery();

            while (rs.next()){
                int cpf = rs.getInt(1);
                String nome = rs.getString(2);
                int telefone = rs.getInt(3);
                int idEndereco = rs.getInt(4);
                donos.add(new DonoCliente(cpf,nome,telefone,endereco.selectId(idEndereco)));

            }
            return donos;

        }catch (SQLException e){
            throw new SQLException("Erro ao buscar animais");
        }

    }
}
