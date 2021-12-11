package com.lhind.application.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Time departureTime;

    @Column(nullable = false)
    private Date arrivalDate;

    @Column(nullable = false)
    private Time arrivalTime;

    @Column(nullable = false)
    private String airline;

    @Column(name = "deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "flights", fetch = FetchType.LAZY)
    private List<Trip> trips = new ArrayList<>();

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;

        return this.from.equals(flight.from)
                && this.to.equals(flight.to)
                && this.departureDate.toLocalDate().isEqual(((Flight) o).departureDate.toLocalDate())
                && this.arrivalDate.toLocalDate().isEqual(((Flight) o).arrivalDate.toLocalDate())
                && this.departureTime.toLocalTime().equals(((Flight) o).departureTime.toLocalTime())
                && this.arrivalTime.toLocalTime().equals(((Flight) o).arrivalTime.toLocalTime())
                && this.airline.equals(flight.airline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, departureDate, arrivalDate, airline, isDeleted, trips);
    }
}
