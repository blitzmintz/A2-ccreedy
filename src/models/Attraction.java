package models;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Attraction {
    private static int maxId = 0;
    private int id;
    private String name;
    private int maxConcurrentVisitors;
    private Employee runBy;
    private Queue<Visitor> visitorsWaiting;
    private LinkedList<Visitor> visitorsVisited;
}
