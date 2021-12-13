package com.lhind.application.entity;

import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE trip SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@NoArgsConstructor
@Getter
@Setter
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TripReason tripReason;

    @Column(nullable = false, length = 256)
    private String description;

    @Column(name = "from_city", nullable = false, length = 20)
    private String from;

    @Column(name = "to_city", nullable = false, length = 20)
    private String to;

    @Column(nullable = false)
    private Date departureDate;

    @Column(nullable = false)
    private Date arrivalDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @Column(name = "deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trip_flight",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private List<Flight> flights = new ArrayList<>();

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Trip trip = (Trip) o;

        return this.tripReason == trip.tripReason
                && this.description.equals(trip.description)
                && this.from.equals(trip.from)
                && this.to.equals(trip.to)
                && this.departureDate.toLocalDate().isEqual(((Trip) o).departureDate.toLocalDate())
                && this.arrivalDate.toLocalDate().isEqual(((Trip) o).arrivalDate.toLocalDate());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
