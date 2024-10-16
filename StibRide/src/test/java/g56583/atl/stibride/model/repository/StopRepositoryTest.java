package g56583.atl.stibride.model.repository;

import java.util.ArrayList;
import java.util.List;

import g56583.atl.stibride.model.dao.StopDao;
import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.model.dto.StopDto;
import javafx.util.Pair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopRepositoryTest {

    @Mock
    private StopDao mock;

    private final StopDto etgNoir;
    private final List<StopDto> all;

    public StopRepositoryTest() {
        all = new ArrayList<>();
        all.add(new StopDto(1, new StationDto(8382, "GARE DE L'OUEST"), 1));
        all.add(new StopDto(1, new StationDto(8742, "BEEKKANT"), 2));
        all.add(new StopDto(2, new StationDto(8764, "SIMONIS"), 1));
        all.add(new StopDto(2, new StationDto(8754, "OSSEGHEM"), 2));
        all.add(new StopDto(2, new StationDto(8742, "BEEKKANT"), 3));
        all.add(new StopDto(2, new StationDto(8382, "GARE DE L'OUEST"), 4));
        all.add(new StopDto(5, new StationDto(8641, "ERASME"), 1));
        all.add(new StopDto(5, new StationDto(8652, "EDDY MERCKX"), 2));
        all.add(new StopDto(6, new StationDto(8833, "ROI BAUDOUIN"), 1));
        all.add(new StopDto(6, new StationDto(8824, "HEYSEL"), 2));
        etgNoir = new StopDto(1, new StationDto(8292, "ETANGS NOIRS"), 3);
        all.add(etgNoir);
    }

    @BeforeEach
    void init() throws RepositoryException {
        List<StopDto> sameMok = new ArrayList<>();
        sameMok.add(all.get(1));
        sameMok.add(all.get(4));

        List<StopDto> adjLocalMok = new ArrayList<>();
        adjLocalMok.add(all.get(10));
        adjLocalMok.add(all.get(1));
        adjLocalMok.add(all.get(3));

        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.selectSame(all.get(1).getKey().getKey())).thenReturn(sameMok);
        Mockito.lenient().when(mock.selectAdj(sameMok)).thenReturn(adjLocalMok);
        Mockito.lenient().when(mock.select(new Pair<>(8382, 1))).thenThrow(UnsupportedOperationException.class);
        Mockito.lenient().when(mock.insert(etgNoir)).thenThrow(UnsupportedOperationException.class);
        Mockito.lenient().when(mock.selectSame(999)).thenReturn(new ArrayList<>());
    }

    @Test
    public void testGetAll() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> result = repository.getAll();

        assertEquals(all, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGetAdj() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> sameStation = repository.getSame(all.get(1).getKey().getKey());
        List<StopDto> adjacent = repository.getAdj(sameStation);

        List<StopDto> adjLocal = new ArrayList<>();
        adjLocal.add(all.get(10));
        adjLocal.add(all.get(1));
        adjLocal.add(all.get(3));

        assertEquals(adjLocal, adjacent);
        Mockito.verify(mock, times(1)).selectAdj(sameStation);
    }

    @Test
    public void testGetSame() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> sameStation = repository.getSame(all.get(1).getKey().getKey());

        List<StopDto> same = new ArrayList<>();
        same.add(all.get(1));
        same.add(all.get(4));

        assertEquals(same, sameStation);
        Mockito.verify(mock, times(1)).selectSame(all.get(1).getKey().getKey());
    }

    @Test
    public void testGetSameNotExist() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> sameStation = repository.getSame(999);
        List<StopDto> same = new ArrayList<>();

        assertEquals(same, sameStation);
        Mockito.verify(mock, times(1)).selectSame(999);
    }

    @Test
    public void testGet() throws Exception {
        StopRepository repository = new StopRepository(mock);

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.get(new Pair<>(8382, 1));
        });
    }

    @Test
    public void testAdd() throws Exception {
        StopRepository repository = new StopRepository(mock);

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.add(etgNoir);
        });
    }
}
