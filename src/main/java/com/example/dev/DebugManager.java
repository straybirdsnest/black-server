package com.example.dev;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DebugManager {
    public List<DebugController.Request> requests = new ArrayList<>();
}


