package app.tracker.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private String nombreProducto;
    private int cantidad;
    private double precio;
    private LocalDate fecha;
}
