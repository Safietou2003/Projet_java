package newstart.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="Detail")
public class Detail extends AbstractEntity{

    private int qteVendu;
    private int prixVente;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Dette dette;

}

