package za.co.four.assignment.edwinBougaard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.four.assignment.edwinBougaard.dao.SpeedDao;
import za.co.four.assignment.edwinBougaard.dao.TrafficDao;
import za.co.four.assignment.edwinBougaard.dao.PlanetDao;
import za.co.four.assignment.edwinBougaard.entity.Speed;
import za.co.four.assignment.edwinBougaard.entity.Traffic;
import za.co.four.assignment.edwinBougaard.entity.Planet;
import za.co.four.assignment.edwinBougaard.helper.Graph;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


@Service
public class EntityManagerService {
    private static final String EXCEL_FILENAME = "/27four IM.xlsx";
    private PlanetDao vertexDao;
    private SpeedDao edgeDao;
    private TrafficDao trafficDao;

    @Autowired
    public EntityManagerService(PlanetDao vertexDao, SpeedDao edgeDao, TrafficDao trafficDao) {
        this.vertexDao = vertexDao;
        this.edgeDao = edgeDao;
        this.trafficDao = trafficDao;
    }

    public void persistGraph() {
        URL resource = getClass().getResource(EXCEL_FILENAME);
        File file1;
        try {
            file1 = new File(resource.toURI());
            persistGraph(file1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void persistGraph(File file) {
        XLSXHandler handler = new XLSXHandler(file);

        List<Planet> vertices = handler.readVertexes();
        if (vertices != null && !vertices.isEmpty()) {
            for (Planet v : vertices) {
                vertexDao.save(v);
            }
        }
        List<Speed> edges = handler.readEdges();
        if (edges != null && !edges.isEmpty()) {
            for (Speed e : edges) {
                edgeDao.save(e);
            }
        }
        List<Traffic> traffic = handler.readTraffics();
        if (edges != null && !edges.isEmpty()) {
            for (Traffic t : traffic) {
                trafficDao.save(t);
            }
        }
    }

    public Graph selectGraph() {
        List<Planet> vertices = vertexDao.selectAll();
        List<Speed> edges = edgeDao.selectAll();
        List<Traffic> traffics = trafficDao.selectAll();

        Graph graph = new Graph(vertices, edges, traffics);

        return graph;
    }

    public Planet saveVertex(Planet vertex) {
        vertexDao.save(vertex);
        return vertex;
    }

    public Planet updateVertex(Planet vertex) {
        vertexDao.update(vertex);
        return vertex;
    }

    public boolean deleteVertex(String vertexId) {
        vertexDao.delete(vertexId);
        return true;
    }

    public List<Planet> getAllVertices() {
        return vertexDao.selectAll();
    }

    public Planet getVertexByName(String name) {
        return vertexDao.selectUniqueByName(name);
    }

    public Planet getVertexById(String vertexId) {
        return vertexDao.selectUnique(vertexId);
    }

    public boolean vertexExist(String vertexId) {
        Planet vertex = vertexDao.selectUnique(vertexId);
        return vertex != null;
    }

    public Speed saveEdge(Speed edge) {
        edgeDao.save(edge);
        return edge;
    }

    public Speed updateEdge(Speed edge) {
        edgeDao.update(edge);
        return edge;
    }

    public boolean deleteEdge(long recordId) {
        edgeDao.delete(recordId);
        return true;
    }

    public List<Speed> getAllEdges() {
        return edgeDao.selectAll();
    }

    public Speed getEdgeById(long recordId) {
        return edgeDao.selectUnique(recordId);
    }

    public long getEdgeMaxRecordId() {
        return edgeDao.selectMaxRecordId();
    }

    public boolean edgeExists(Speed edge) {
        List<Speed> edges = edgeDao.edgeExists(edge);
        return !edges.isEmpty();
    }

    public Traffic saveTraffic(Traffic traffic) {
        trafficDao.save(traffic);
        return traffic;
    }

    public Traffic updateTraffic(Traffic traffic) {
        trafficDao.update(traffic);
        return traffic;
    }

    public boolean deleteTraffic(String routeId) {
        trafficDao.delete(routeId);
        return true;
    }

    public List<Traffic> getAllTraffics() {
        return trafficDao.selectAll();
    }

    public Traffic getTrafficById(String routeId) {
        return trafficDao.selectUnique(routeId);
    }

    public long getTrafficMaxRecordId() {
        return trafficDao.selectMaxRecordId();
    }

    public boolean trafficExists(Traffic traffic) {
        List<Traffic> traffics = trafficDao.trafficExists(traffic);
        return !traffics.isEmpty();
    }
}
