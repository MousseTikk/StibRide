package g56583.atl.stibride.model.dto;
import java.util.Objects;
import javafx.util.Pair;

// Since the key is composed, we use a pair. We could have added a column
// (in auto-increment) for example. Here it is not a problem since we do not do
// a single insert / delete / delete or select, but it is better to be
// faithful to the repository architecture.
public class StopDto extends Dto<Pair<Integer, Integer>> {
    private final StationDto station;
    private final int line;
    private final int order;

    public StopDto(int line, StationDto station, int order) {
        super(new Pair<>(station.getKey(), line));
        this.station = station;
        this.line = line;
        this.order = order;
    }

    public StationDto getStation() {
        return station;
    }

    public int getLine() {
        return line;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StopDto stopDto = (StopDto) o;
        return line == stopDto.line && order == stopDto.order && Objects.equals(station, stopDto.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), station, line, order);
    }
}
