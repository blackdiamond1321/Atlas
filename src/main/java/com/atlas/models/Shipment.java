package com.atlas.models;

import lombok.Value;

@Value
public class Shipment {
    String name;
    double weight;
    double distance;
    String destination;
    String type;
}
