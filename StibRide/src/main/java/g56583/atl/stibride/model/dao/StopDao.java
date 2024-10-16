package g56583.atl.stibride.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.model.dto.StopDto;
import g56583.atl.stibride.model.repository.DBManager;
import g56583.atl.stibride.model.repository.RepositoryException;
import javafx.util.Pair;

public class StopDao implements Dao<Pair<Integer, Integer>, StopDto> {

    private final Connection connexion;

    public StopDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StopDao getInstance() throws RepositoryException {
        return StopDaoHolder.getInstance();
    }

    @Override
    public void delete(Pair<Integer, Integer> key) throws RepositoryException {
        throw new UnsupportedOperationException("Delete one element is not used here");
    }

    @Override
    public StopDto select(Pair<Integer, Integer> key) throws RepositoryException {
        throw new UnsupportedOperationException("Select one element is not used here");
    }

    @Override
    public Pair<Integer, Integer> insert(StopDto item) throws RepositoryException {
        throw new UnsupportedOperationException("Insert one element is not used here");
    }

    @Override
    public void update(StopDto item) throws RepositoryException {
        throw new UnsupportedOperationException("Update one element is not used here");
    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        String sql = "SELECT id_line, id_station, id_order, name FROM stops JOIN "
                + "lines line ON line.id = id_line " +
                "JOIN stations station ON station.id = id_station ORDER BY id_line, id_order";
        List<StopDto> stops = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"), rs.getString("name")),
                        rs.getInt("id_order"));
                stops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stops;
    }

    public List<StopDto> selectSame(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Erreur avec les objets donnés");
        }
        String sql = "SELECT id_line, id_station, id_order, name FROM stops JOIN "
                + "lines line ON line.id = id_line " +
                "JOIN stations station ON station.id = id_station WHERE "
                + "id_station = ? ORDER BY id_line, id_order";
        List<StopDto> stops = new ArrayList<>();
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"), rs.getString("name")),
                        rs.getInt("id_order"));
                stops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stops;
    }

    public List<StopDto> selectAdj(List<StopDto> stops) throws RepositoryException {
        if (stops == null) {
            throw new RepositoryException("Erreur avec les objets donnés");
        }
        var sql = new StringBuilder("SELECT id_line, id_station, id_order, name "
                + "FROM stops JOIN lines line ON " +
                "line.id = id_line JOIN stations station ON station.id = id_station WHERE");
        List<Integer> preparateList = new ArrayList<>();
        stops.forEach(stop -> {
            if (stops.get(0) != stop) {
                sql.append(" OR ");
            }
            sql.append("(id_line = ? AND (id_order = ? OR id_order = ?))");
            preparateList.add(stop.getLine());
            preparateList.add(stop.getOrder() - 1);
            preparateList.add(stop.getOrder() + 1);
        });
        sql.append(" GROUP BY name");

        List<StopDto> prevNextStops = new ArrayList<>();
        try (PreparedStatement pstmt = connexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < preparateList.size(); i++) {
                pstmt.setInt(i + 1, preparateList.get(i));
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"), rs.getString("name")),
                        rs.getInt("id_order"));
                prevNextStops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return prevNextStops;
    }

    private static class StopDaoHolder {

        private static StopDao getInstance() throws RepositoryException {
            return new StopDao();
        }
    }
}