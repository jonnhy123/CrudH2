package crudh2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrudH2 {

    private Connection con;

    public CrudH2() {
        conectar();

//        for (int i = 1; i < 20; i++) {
//            Aluno a = new Aluno(i, "Nome " + i);
//            inserir(a);
//        }
        
//        for (int i = 1; i < 20; i++) {
//        	apagar(i);
//		}
        
        System.out.println(buscarUm(5)+"\n");
        
        
//        atualizar("Teste_Up", 19);
        
//        List<Aluno> lista = buscarTodos();
//        
//        for (Aluno aluno : lista) {
//            System.out.println(aluno);
//        }

        desconectar();
    }


	public void conectar() {
        String url = "jdbc:h2:~/aula";
        String user = "sa";
        String pass = "sa";
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(CrudH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CrudH2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (id, nome) VALUES (?, ?)";
        
        PreparedStatement ps;
        try {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setString(2, aluno.getNome());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CrudH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizar(String nome, int id) {
    	String sql = "UPDATE ALUNO SET NOME = ? WHERE ID = ?";
    	PreparedStatement ps;
    	
    	try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
    	} catch (SQLException ex) {
            Logger.getLogger(CrudH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void apagar(int id) {
    	String sql = "DELETE FROM ALUNO WHERE ID = ?";
    	
    	PreparedStatement ps;
    	try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public List<Aluno> buscarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aluno a = new Aluno(0, null);
                a.setId(rs.getInt(1));
                a.setNome(rs.getString(2));
                lista.add(a);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CrudH2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Aluno buscarUm(int id) {
    	String sql = "SELECT * FROM ALUNO WHERE ID = ?";
    	PreparedStatement ps;
    	Aluno aluno = null;
    	try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aluno = new Aluno(id, null);
                aluno.setId(id);
                aluno.setNome(rs.getString("nome"));
            }
            } catch (SQLException ex) {
            Logger.getLogger(CrudH2.class.getName()).log(Level.SEVERE, null, ex);
        }
		return aluno;
    }

    public static void main(String[] args) {
        new CrudH2();
    }
}
