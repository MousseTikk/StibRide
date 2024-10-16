package g56583.atl.stibride.model.dto;

import java.util.HashSet;
import java.util.Set;

public class StationDto extends Dto<Integer> {
    private final String name;
    private Set<Integer> lines;

    public StationDto(int key, String name) {
        super(key);
        this.name = name;
        this.lines = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Integer getKey() {
        return super.key;
    }

    public Set<Integer> getLines() {
        return lines;
    }

    public void setLines(Set<Integer> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getLinesToString() {
        var strStops = new StringBuilder("[");
        for (var line : lines) {
            strStops.append(line).append(", ");
        }
        String str = strStops.toString();
        if (strStops.length() > 1) {
            str = str.substring(0, str.length() - 2);
        }
        return str + "]";
    }
}
