package com.powerreaderapi.powerreaderapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "power_reader")
@Getter
@Setter
@NoArgsConstructor
public class PowerReader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String serialNumber;

    @OneToMany(mappedBy = "powerReader", fetch = FetchType.EAGER)
    private List<Device> connectedDevices = new LinkedList<>();

    public PowerReader(String name, String serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }
}
