package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.State;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreightService {



    private static final Double STANDARD_RATE = 0.015;
    private static final Map<State, Double> DISTANCE_MAP = new HashMap<>();

    static {
        DISTANCE_MAP.put(State.AL, 0.0);
        DISTANCE_MAP.put(State.SE, 279.0);
        DISTANCE_MAP.put(State.BA, 580.0);
        DISTANCE_MAP.put(State.PI, 1202.0);
        DISTANCE_MAP.put(State.PE, 258.0);
        DISTANCE_MAP.put(State.PB, 382.0);
        DISTANCE_MAP.put(State.RN, 540.0);
        DISTANCE_MAP.put(State.CE, 1035.0);
        DISTANCE_MAP.put(State.MA, 1631.0);
        DISTANCE_MAP.put(State.TO, 1899.0);
        DISTANCE_MAP.put(State.PA, 1679.0);
        DISTANCE_MAP.put(State.AP, 2010.0);
        DISTANCE_MAP.put(State.RR, 3096.0);
        DISTANCE_MAP.put(State.AM, 2780.0);
        DISTANCE_MAP.put(State.AC, 3515.0);
        DISTANCE_MAP.put(State.RO, 3093.0);
        DISTANCE_MAP.put(State.MT, 2307.0);
        DISTANCE_MAP.put(State.GO, 1663.0);
        DISTANCE_MAP.put(State.DF, 1962.0);
        DISTANCE_MAP.put(State.MS, 2355.0);
        DISTANCE_MAP.put(State.MG, 1807.0);
        DISTANCE_MAP.put(State.ES, 1672.0);
        DISTANCE_MAP.put(State.RJ, 2128.0);
        DISTANCE_MAP.put(State.SP, 1937.0);
        DISTANCE_MAP.put(State.PR, 2264.0);
        DISTANCE_MAP.put(State.SC, 2407.0);
        DISTANCE_MAP.put(State.RS, 2780.0);
    }

    public Double calculateFreight(State clientState) {

        Double distance = DISTANCE_MAP.get(clientState);
        Double freightCost = distance * STANDARD_RATE;

        BigDecimal freightCostRounded = new BigDecimal(freightCost).setScale(2, RoundingMode.HALF_UP);

        return freightCostRounded.doubleValue();
    }
}
