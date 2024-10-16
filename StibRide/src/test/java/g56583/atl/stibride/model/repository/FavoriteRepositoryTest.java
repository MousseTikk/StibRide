package g56583.atl.stibride.model.repository;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import g56583.atl.stibride.model.dao.FavoriteDao;
import g56583.atl.stibride.model.dto.FavoriteDto;
import g56583.atl.stibride.model.dto.StationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class FavoriteRepositoryTest {

    @Mock
    private FavoriteDao mock;

    private final FavoriteDto home;
    private final FavoriteDto notExist;

    private final List<FavoriteDto> all;

    private FavoriteDao instance;

    public FavoriteRepositoryTest() {
        notExist = new FavoriteDto("notExist", new StationDto(8292, "DE BROUCKERE"), new StationDto(8382, "GARE DE L'OUEST"));
        all = new ArrayList<>();
        all.add(new FavoriteDto("other", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM")));
        home = new FavoriteDto("home", new StationDto(8641, "ERASME"), new StationDto(8742, "BEEKKANT"));
        all.add(home);
    }

    @BeforeEach
    void init() throws RepositoryException {
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select("home")).thenReturn(home);
    }

    @Test
    public void testGetAll() throws Exception {
        FavoriteRepository repository = new FavoriteRepository(mock);
        List<FavoriteDto> result = repository.getAll();

        assertEquals(all, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGet() throws Exception {
        FavoriteRepository repository = new FavoriteRepository(mock);
        FavoriteDto result = repository.get("home");

        assertEquals(home, result);
        Mockito.verify(mock, times(1)).select("home");
    }

    @Test
    public void testAdd() throws Exception {
        FavoriteRepository repository = new FavoriteRepository(mock);
        var newFavorite = new FavoriteDto("added0", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        repository.add(newFavorite);

        Mockito.verify(mock, times(1)).insert(any(FavoriteDto.class));
    }

    @Test
    public void testRemove() throws Exception {
        FavoriteRepository repository = new FavoriteRepository(mock);
        repository.remove("home");

        Mockito.verify(mock, times(1)).delete("home");
    }

    @Test
    public void testUpdate() throws Exception {
        FavoriteRepository repository = new FavoriteRepository(mock);
        var editedFavorite = new FavoriteDto("home", new StationDto(8292, "DE BROUCKERE"), new StationDto(8382, "GARE DE L'OUEST"));
        repository.update(editedFavorite);

        Mockito.verify(mock, times(1)).update(editedFavorite);
    }
}
