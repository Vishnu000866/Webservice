package services;

import java.util.List;

import data.Lego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/lego")
public class LegoService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lego");

    @Path("/getlego")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getLego() {
        return "Lego service is running!";
    }

    // Browser sends robot settings here
    @Path("/setvalues")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Lego setValues(Lego lego) {
        EntityManager em = emf.createEntityManager();

        try {
            lego.setSource("UI");

            em.getTransaction().begin();
            em.persist(lego);
            em.getTransaction().commit();

            return lego;
        } finally {
            em.close();
        }
    }

    // EV3 robot reads latest browser settings from here
    @SuppressWarnings("unchecked")
    @Path("/getvalues")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getValues() {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery(
                "select s from Lego s where s.source = 'UI' order by s.id desc"
            ).setMaxResults(1);

            List<Lego> list = q.getResultList();

            if (list.isEmpty()) {
                return "0#0#0#0#20#0";
            }

            Lego lego = list.get(0);

            return lego.getRun() + "#"
                    + lego.getSpeed() + "#"
                    + lego.getTurn() + "#"
                    + lego.getMode() + "#"
                    + lego.getObstacleLimit() + "#"
                    + lego.getAvoidAction();

        } finally {
            em.close();
        }
    }

    // Browser can see latest settings as JSON
    @SuppressWarnings("unchecked")
    @Path("/currentsettings")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Lego getCurrentSettings() {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery(
                "select s from Lego s where s.source = 'UI' order by s.id desc"
            ).setMaxResults(1);

            List<Lego> list = q.getResultList();

            if (list.isEmpty()) {
                Lego lego = new Lego();
                lego.setSource("UI");
                lego.setRun(0);
                lego.setSpeed(120);
                lego.setTurn(0);
                lego.setMode(0);
                lego.setObstacleLimit(20);
                lego.setAvoidAction(1);
                return lego;
            }

            return list.get(0);

        } finally {
            em.close();
        }
    }

    // EV3 robot sends its data here
    @Path("/senddata")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Lego sendData(Lego lego) {
        EntityManager em = emf.createEntityManager();

        try {
            lego.setSource("ROBOT");

            em.getTransaction().begin();
            em.persist(lego);
            em.getTransaction().commit();

            return lego;
        } finally {
            em.close();
        }
    }

    // Browser reads latest robot data
    @SuppressWarnings("unchecked")
    @Path("/robotdata")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Lego getRobotData() {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery(
                "select s from Lego s where s.source = 'ROBOT' order by s.id desc"
            ).setMaxResults(1);

            List<Lego> list = q.getResultList();

            if (list.isEmpty()) {
                Lego lego = new Lego();
                lego.setSource("ROBOT");
                lego.setMessage("No robot data yet");
                return lego;
            }

            return list.get(0);

        } finally {
            em.close();
        }
    }

    // Browser shows simple statistics
    @SuppressWarnings("unchecked")
    @Path("/statistics")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatistics() {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery(
                "select s from Lego s where s.source = 'ROBOT'"
            );

            List<Lego> list = q.getResultList();

            if (list.isEmpty()) {
                return "No robot statistics yet.";
            }

            int count = list.size();
            int speedTotal = 0;
            int obstacleCount = 0;
            int lineCount = 0;

            for (Lego lego : list) {
                speedTotal += lego.getSpeed();

                if (lego.getObstacleDetected() == 1) {
                    obstacleCount++;
                }

                if (lego.getLineDetected() == 1) {
                    lineCount++;
                }
            }

            double averageSpeed = (double) speedTotal / count;

            return "Robot data rows: " + count
                    + "\nAverage speed: " + averageSpeed
                    + "\nObstacle detections: " + obstacleCount
                    + "\nLine detections: " + lineCount;

        } finally {
            em.close();
        }
    }

    // Browser can print last 20 rows
    @SuppressWarnings("unchecked")
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lego> getAllData() {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery(
                "select s from Lego s order by s.id desc"
            ).setMaxResults(20);

            return q.getResultList();

        } finally {
            em.close();
        }
    }
}