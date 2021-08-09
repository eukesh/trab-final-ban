package database;

import model.Animal;
import model.DonoCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EDonoDAO {


    private static EDonoDAO instance = null;


    private final PreparedStatement selectNewId;
    private final PreparedStatement insert;

    public static EDonoDAO getInstace() throws ClassNotFoundException, SQLException {
        if(instance == null){
            instance = new EDonoDAO();
        }
        return instance;
    }

    private EDonoDAO() throws ClassNotFoundException, SQLException {
        Connection conexao = ConectionBd.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_edono')");
        insert = conexao.prepareStatement("insert into edono values(?,?,?)");
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

    public void insert(DonoCliente dono, Animal animal) throws SQLException {
        try{
            insert.setInt(1,selectNewId());
            insert.setInt(2,dono.getCpf());
            insert.setInt(3,animal.getId());
            insert.executeUpdate();

        }catch (SQLException e){
            throw new SQLException("Erro ao inserir relacionamento");
        }
    }

}
