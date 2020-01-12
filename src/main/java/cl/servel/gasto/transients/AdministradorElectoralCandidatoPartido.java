package cl.servel.gasto.transients;

import cl.servel.gasto.entity.AdmAdministradorElectoral;
import cl.servel.gasto.entity.CanCandidato;
import cl.servel.gasto.entity.ParPartido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdministradorElectoralCandidatoPartido {

    private AdmAdministradorElectoral admAdministradorElectoral;
    private CanCandidato canCandidato;
    private ParPartido parPartido;
}