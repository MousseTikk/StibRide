package g56583.atl.stibride.model.repository;

import java.util.List;

import g56583.atl.stibride.model.dao.StopDao;
import g56583.atl.stibride.model.dto.StopDto;
import javafx.util.Pair;

/**
 * @author jlc
 */
public class StopRepository implements Repository<Pair<Integer, Integer>, StopDto> {

    private final StopDao dao;

    public StopRepository(StopDao dao) {
        this.dao = dao;
    }

    public StopRepository() throws RepositoryException {
        dao = StopDao.getInstance();
    }

    @Override
    public void update(StopDto item) throws RepositoryException {
        dao.update(item);
    }

    @Override
    public Pair<Integer, Integer> add(StopDto item) throws RepositoryException {
        return dao.insert(item);
    }

    @Override
    public void remove(Pair<Integer, Integer> key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    public List<StopDto> getSame(Integer key) throws RepositoryException {
        return dao.selectSame(key);
    }

    public List<StopDto> getAdj(List<StopDto> stops) throws RepositoryException {
        return dao.selectAdj(stops);
    }
}
