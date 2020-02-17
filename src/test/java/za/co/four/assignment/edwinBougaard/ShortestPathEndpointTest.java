package za.co.four.assignment.edwinBougaard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import za.co.four.assignment.edwinBougaard.config.DatasourceBean;
import za.co.four.assignment.edwinBougaard.config.PersistenceBean;
import za.co.four.assignment.edwinBougaard.config.WebServiceBean;
import za.co.four.assignment.edwinBougaard.dao.PlanetDao;
import za.co.four.assignment.edwinBougaard.dao.SpeedDao;
import za.co.four.assignment.edwinBougaard.dao.TrafficDao;
import za.co.four.assignment.edwinBougaard.schema.GetShortestPathRequest;
import za.co.four.assignment.edwinBougaard.schema.GetShortestPathResponse;
import za.co.four.assignment.edwinBougaard.service.EntityManagerService;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatasourceBean.class, PersistenceBean.class, WebServiceBean.class,
        ShortestPathEndpoint.class, ShortestPathRepository.class, EntityManagerService.class, SpeedDao.class, PlanetDao.class,
        TrafficDao.class},
        loader = AnnotationConfigContextLoader.class)
public class ShortestPathEndpointTest {

    @Autowired
    private ShortestPathEndpoint shortestPathEndpoint;

    @Test
    public void verifyThatShortestPathSOAPEndPointIsCorrect() throws Exception {
        // Set Up Fixture
        GetShortestPathRequest shortestPathRequest = new GetShortestPathRequest();
        shortestPathRequest.setName("Moon");

        StringBuilder path = new StringBuilder();
        path.append("Earth (A)\tMoon (B)\t");

        GetShortestPathResponse expectedResponse = new GetShortestPathResponse();
        expectedResponse.setPath(path.toString());

        //Test
        GetShortestPathResponse actualResponse = shortestPathEndpoint.getShortestPath(shortestPathRequest);

        // Verify
        assertThat(actualResponse, sameBeanAs(expectedResponse));
        assertThat(actualResponse.getPath(), sameBeanAs("Earth (A)\tMoon (B)\t"));
    }

}