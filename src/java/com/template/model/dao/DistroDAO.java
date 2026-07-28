package com.template.model.dao;

import com.template.model.Conn;
import com.template.model.dto.DistroDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.template.util.DialogUtil.showError;

public class DistroDAO {

    // Registrar Distro (Create)
    public void registerDistro(DistroDTO distro) {
        String sql = "INSERT INTO distro (name, base, package_manager, environment) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = new Conn().connectDB();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, distro.getName());
            preparedStatement.setString(2, distro.getBase());
            preparedStatement.setString(3, distro.getPackageManager());
            preparedStatement.setString(4, distro.getEnvironment());

            preparedStatement.execute();
        } catch (SQLException e) {
            Logger.getLogger(DistroDTO.class.getName()).log(Level.SEVERE, null, e);
            showError("Erro ao registrar distro.");
        }
    }

    // Selecionar (listar) Distros (Read)
    public ArrayList<DistroDTO> selectDistro() {
        String sql = "SELECT * FROM distro";
        ArrayList<DistroDTO> distros = new ArrayList<>();

        try (
                Connection connection = new Conn().connectDB();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()

        ) {
            while (resultSet.next()) {
                DistroDTO distro = new DistroDTO();
                distro.setId(resultSet.getInt("id"));
                distro.setName(resultSet.getString("name"));
                distro.setBase(resultSet.getString("base"));
                distro.setPackageManager(resultSet.getString("package_manager"));
                distro.setEnvironment(resultSet.getString("environment"));
                distros.add(distro);
            }

        } catch (SQLException e) {
            Logger.getLogger(DistroDTO.class.getName()).log(Level.SEVERE, null, e);
            showError("Erro ao listar distros.");
        }

        return distros;
    }

    // Atualizar Distro (Read)
    public void updateDistro(DistroDTO distro) {
        String sql = "UPDATE distro SET name = ?, base = ?, package_manager = ?, environment = ? WHERE id = ?";

        try (
                Connection connection = new Conn().connectDB();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, distro.getName());
            preparedStatement.setString(2, distro.getBase());
            preparedStatement.setString(3, distro.getPackageManager());
            preparedStatement.setString(4, distro.getEnvironment());
            preparedStatement.setInt(5, distro.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            Logger.getLogger(DistroDTO.class.getName()).log(Level.SEVERE, null, e);
            showError("Erro ao atualizar distro.");
        }
    }

    // Excluir Distro (Delete)
    public void deleteDistro(DistroDTO distro) {
        String sql = "DELETE FROM distro WHERE id = ?";

        try (
                Connection connection = new Conn().connectDB();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, distro.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            Logger.getLogger(DistroDTO.class.getName()).log(Level.SEVERE, null, e);
            showError("Erro ao excluir distro.");
        }
    }
}

