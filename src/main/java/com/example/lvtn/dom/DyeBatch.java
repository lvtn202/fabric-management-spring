package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "dye_batch")
@Getter
@Setter
public class DyeBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dye_date", nullable = false)
    private Timestamp dyeDate;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "import_slip_id", nullable = false)
    private ImportSlip importSlip;

    public DyeBatch() {
    }

    @Override
    public String toString() {
        return "DyeBatch{" +
                "id=" + id +
                ", dyeDate=" + dyeDate +
                ", color=" + color +
                ", dyehouse=" + dyehouse +
                ", importSlip=" + importSlip +
                '}';
    }
}
