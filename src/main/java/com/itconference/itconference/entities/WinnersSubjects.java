package com.itconference.itconference.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "winners_subjects")
public class WinnersSubjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Winners winners;

    @OneToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subjects subjects;
}