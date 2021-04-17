package com.example.lvtn.dom;

import com.example.lvtn.utils.FabricStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "fabric")
@Getter
@Setter
public class Fabric {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "raw_length", nullable = false)
    private Double rawLength;

    @Column(name = "finished_length")
    private  Double finishedLength;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private FabricStatus status;

    @Column(name = "color_name", length = 50)
    private String colorName;

    @ManyToOne
    @JoinColumn(name = "fabric_type_id", nullable = false)
    private FabricType fabricType;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "dye_batch_id")
    private DyeBatch dyeBatch;

    @ManyToOne
    @JoinColumn(name = "export_slip_id")
    private ExportSlip exportSlip;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id")
    private Dyehouse dyehouse;

    @OneToMany(mappedBy = "fabric")
    private Set<Return> aReturn;

    public Fabric() {
    }

    public Fabric(Double rawLength, Double finishedLength, FabricStatus status, String colorName, FabricType fabricType, Color color, DyeBatch dyeBatch, ExportSlip exportSlip, Dyehouse dyehouse, Set<Return> aReturn) {
        this.rawLength = rawLength;
        this.finishedLength = finishedLength;
        this.status = status;
        this.colorName = colorName;
        this.fabricType = fabricType;
        this.color = color;
        this.dyeBatch = dyeBatch;
        this.exportSlip = exportSlip;
        this.dyehouse = dyehouse;
        this.aReturn = aReturn;
    }

    @Override
    public String toString() {
        return "Fabric{" +
                "id=" + id +
                ", rawLength=" + rawLength +
                ", finishedLength=" + finishedLength +
                ", status='" + status + '\'' +
                ", colorName='" + colorName + '\'' +
                ", fabricType=" + fabricType +
                ", color=" + color +
                ", dyeBatch=" + dyeBatch +
                ", exportSlip=" + exportSlip +
                ", dyehouse=" + dyehouse +
                '}';
    }
}
