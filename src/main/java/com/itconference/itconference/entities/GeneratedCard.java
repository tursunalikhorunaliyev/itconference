package com.itconference.itconference.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "generated_card", indexes = {
        @Index(name = "idx_generatedcard_cardid", columnList = "cardID")
})
public class GeneratedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long cardID;


}