package za.co.four.assignment.edwinBougaard.service;

import org.springframework.stereotype.Service;
import za.co.four.assignment.edwinBougaard.entity.Speed;
import za.co.four.assignment.edwinBougaard.entity.Planet;
import za.co.four.assignment.edwinBougaard.helper.Graph;

import java.util.*;


@Service
public class ShortestPathService {

    private List<Planet> vertices;
    private List<Speed> edges;
    private Set<Planet> visitedVertices;
    private Set<Planet> unvisitedVertices;
    private Map<Planet, Planet> previousPaths;
    private Map<Planet, Float> distance;

    public ShortestPathService() {
    }

    public ShortestPathService(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.edges = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.edges = new ArrayList<>(graph.getEdges());
        }
    }

    public void initializePlanets(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.edges = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.edges = new ArrayList<>(graph.getEdges());
        }
    }

    public void run(Planet source) {
        distance = new HashMap<>();
        previousPaths = new HashMap<>();
        visitedVertices = new HashSet<>();
        unvisitedVertices = new HashSet<>();
        distance.put(source, 0f);
        unvisitedVertices.add(source);
        while (unvisitedVertices.size() > 0) {
            Planet currentVertex = getVertexWithLowestDistance(unvisitedVertices);
            visitedVertices.add(currentVertex);
            unvisitedVertices.remove(currentVertex);
            evaluateNeighborsWithMinimalDistances(currentVertex);
        }
    }

    private Planet getVertexWithLowestDistance(Set<Planet> vertexes) {
        Planet lowestVertex = null;
        for (Planet vertex : vertexes) {
            if (lowestVertex == null) {
                lowestVertex = vertex;
            } else if (getShortestDistance(vertex) < getShortestDistance(lowestVertex)) {
                lowestVertex = vertex;
            }
        }
        return lowestVertex;
    }

    private void evaluateNeighborsWithMinimalDistances(Planet currentVertex) {
        List<Planet> adjacentVertices = getNeighbors(currentVertex);
        for (Planet target : adjacentVertices) {
            float alternateDistance = getShortestDistance(currentVertex) + getDistance(currentVertex, target);
            if (alternateDistance < getShortestDistance(target)) {
                distance.put(target, alternateDistance);
                previousPaths.put(target, currentVertex);
                unvisitedVertices.add(target);
            }
        }
    }

    private List<Planet> getNeighbors(Planet currentVertex) {
        List<Planet> neighbors = new ArrayList<>();
        for (Speed edge : edges) {
            Planet destination = fromId(edge.getDestination());
            if (edge.getSource().equals(currentVertex.getVertexId()) && !isVisited(destination)) {
                neighbors.add(destination);
            }
        }
        return neighbors;
    }

    public Planet fromId(final String str) {
        for (Planet v : vertices) {
            if (v.getVertexId().equalsIgnoreCase(str)) {
                return v;
            }
        }
        Planet islandVertex = new Planet();
        islandVertex.setVertexId(str);
        islandVertex.setName("Island " + str);
        return islandVertex;
    }

    private boolean isVisited(Planet vertex) {
        return visitedVertices.contains(vertex);
    }

    private Float getShortestDistance(Planet destination) {
        Float d = distance.get(destination);
        if (d == null) {
            return Float.POSITIVE_INFINITY;
        } else {
            return d;
        }
    }

    private float getDistance(Planet source, Planet target) {
        for (Speed edge : edges) {
            if (edge.getSource().equals(source.getVertexId()) && edge.getDestination().equals(target.getVertexId())) {
                return edge.getDistance() + edge.getTimeDelay();
            }
        }
        throw new RuntimeException("Error: Something went wrong!");
    }

    public LinkedList<Planet> getPath(Planet target) {
        LinkedList<Planet> path = new LinkedList<>();
        Planet step = target;

        if (previousPaths.get(step) == null) {
            return null;
        }
        path.add(step);
        while (previousPaths.get(step) != null) {
            step = previousPaths.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

}
