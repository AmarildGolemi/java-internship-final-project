package com.lhind.application.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE flight SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@NoArgsConstructor
@Getter
@Setter
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_city", nullable = false, length = 20)
    private String from;

    @Column(name = "to_city", nullable = false, length = 20)
    private String to;

    @Column(nullable = false)
    private Date departureDate;

    @Column(nullable = false)
    private Date arrivalDate;

    @Column(name = "deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "flights", fetch = FetchType.LAZY)
    private List<Trip> trips = new ArrayList<>();

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

}
