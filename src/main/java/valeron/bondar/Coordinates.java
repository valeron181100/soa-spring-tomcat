package valeron.bondar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    private Integer coordinatesId;

    private Double x; //Поле не может быть null

    private Integer y; //Максимальное значение поля: 350, Поле не может быть null
}
