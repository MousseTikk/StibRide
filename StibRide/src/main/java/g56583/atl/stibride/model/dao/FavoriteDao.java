package g56583.atl.stibride.model.dao;

import g56583.atl.stibride.model.dto.FavoriteDto;
import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.model.repository.DBManager;
import g56583.atl.stibride.model.repository.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao implements Dao<String, FavoriteDto> {
    private Connection connexion;

    private FavoriteDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static FavoriteDao getInstance() throws RepositoryException {
        return FavoriteDao.FavoriteDaoHolder.getInstance();
    }

    @Override
    public List<FavoriteDto> selectAll() throws RepositoryException {
        String sql = "SELECT src.name src_name, dest.name dest_name, id_source, "
                + "id_destination, favorite_name " +
                "FROM favorites JOIN stations src ON id_source = src.id JOIN "
                + "stations dest ON id_destination = dest.id";
        List<FavoriteDto> favorites = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                FavoriteDto dto = new FavoriteDto(rs.getString("favorite_name"),
                        new StationDto(rs.getInt("id_source"), rs.getString("src_name")),
                        new StationDto(rs.getInt("id_destination"), rs.getString("dest_name")));
                favorites.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return favorites;
    }

    @Override
    public FavoriteDto select(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Erreur avec les objets donnés");
        }
        String sql = "SELECT src.name src_name, dest.name dest_name, id_source, "
                + "id_destination, favorite_name FROM " +
                "favorites JOIN stations src ON id_source = src.id JOIN stations "
                + "dest ON id_destination = dest.id WHERE favorite_name = ?";
        FavoriteDto favorite = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, key);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                favorite = new FavoriteDto(rs.getString("favorite_name"),
                        new StationDto(rs.getInt("id_source"), rs.getString("src_name")),
                        new StationDto(rs.getInt("id_destination"), rs.getString("dest_name")));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Record pas unique " + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return favorite;
    }

    @Override
    public String insert(FavoriteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        Integer id = 0;
        String sql = "INSERT INTO favorites(id_source, id_destination, favorite_name) "
                + "values(?, ?, ?)";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, item.getSource().getKey());
            pstmt.setInt(2, item.getDestination().getKey());
            pstmt.setString(3, item.getKey());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return item.getKey();
    }

    @Override
    public void delete(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "DELETE FROM favorites WHERE favorite_name = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(FavoriteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "UPDATE favorites SET id_source = ?, id_destination = ?, "
                + "favorite_name = ? where favorite_name = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, item.getSource().getKey());
            pstmt.setInt(2, item.getDestination().getKey());
            pstmt.setString(3, item.getKey());
            pstmt.setString(4, item.getKey());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    private static class FavoriteDaoHolder {

        private static FavoriteDao getInstance() throws RepositoryException {
            return new FavoriteDao();
        }
    }

}
