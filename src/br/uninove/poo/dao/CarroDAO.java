/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uninove.poo.dao;

import br.uninove.poo.negocio.Carro;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Internet
 */
public class CarroDAO {

    public List<Carro> getListarCarro() {
        // conectar o banco atravez da classe conexao
        Conexao conn = new Conexao();
        Connection conexao = conn.getConexao();

        //select na tabela carro
        String sql = "SELECT * FROM carros";

        List<Carro> listCarros = new ArrayList<Carro>();

        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                Carro objCarro = new Carro();
                objCarro.setIdCarro(resultado.getInt("idCarro"));
                objCarro.setAnoFabricacao(resultado.getInt("anoFabricacao"));
                objCarro.setCor(resultado.getString("cor"));
                objCarro.setFabricante(resultado.getString("fabricante"));
                objCarro.setKm(resultado.getDouble("km"));
                objCarro.setModelo(resultado.getString("modelo"));
                objCarro.setValor(resultado.getInt("valor"));

                //insira carro na lista local
                listCarros.add(objCarro);

            }
        } catch (SQLException ex) {

        }
        return listCarros;
    }

    public boolean inserir(Carro objCarro) {
        try {
            Conexao conn = new Conexao();
            Connection conexao = conn.getConexao();
            String sql = "INSERT INTO `carros`(`fabricante`, `modelo`, `anoFabricacao`, `cor`, `km`, `valor`)";

            sql += " VALUES(";
            sql += "'" + objCarro.getFabricante() + "',"; //varchar 
            sql += "'" + objCarro.getModelo() + "',"; //varchar 
            sql += objCarro.getAnoFabricacao() + ","; //inteiro 
            sql += "'" + objCarro.getCor() + "',"; //varchar 
            sql += objCarro.getKm() + ","; //decimal
            sql += objCarro.getValor(); //decimal
            sql += " );";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean atualizar(Carro objCarro) {
        Conexao conn = new Conexao();

        //trycatch tab + tab =  criar a sintaxe;
        try {
            Connection conexao = conn.getConexao();
            String sql = "Update carros set ";
            sql += " fabricante= '" + objCarro.getFabricante() + "', "; // varchar
            sql += " modelo= '" + objCarro.getModelo() + "', ";
            sql += " anoFabricacao= " + objCarro.getAnoFabricacao() + ","; //inteiro nao precisa de apostrofe
            sql += " cor= '" + objCarro.getCor() + "', ";//varchar
            sql += " km= " + objCarro.getKm() + " ,";//decimal
            sql += " valor" + objCarro.getValor();//deicmal. No ultimo nao há necessidade de virgula.
            sql += " where  idCarro = " + objCarro.getIdCarro() + ";";//';' para fechar o where.

            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.executeUpdate(sql);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean delete(int idCarro) {
        String sql = "delete from carros whare idCarro = ?";
        Conexao conn = new Conexao();

        try {
            Connection conexao = conn.getConexao();
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setInt(1, idCarro);
            comando.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

}
