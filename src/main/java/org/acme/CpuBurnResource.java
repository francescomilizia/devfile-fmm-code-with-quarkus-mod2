package org.acme;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


@Path("/cpu")
public class CpuBurnResource {

    private static final long MILLIS_DEFAULT = 5000;
	private static final int CORES_DEFAULT = 1;
    private final Random random = new Random();

	@GET
    @Path("/burn")
    public Response burnCpu(@QueryParam("ms") long milliseconds) {
        if (milliseconds <= 0) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                           .entity("Il valore deve essere maggiore di 0")
//                           .build();
        	milliseconds=MILLIS_DEFAULT;
        }

        long end = System.currentTimeMillis() + milliseconds;
        while (System.currentTimeMillis() < end) {
            double waste = Math.sqrt(Math.random()) * Math.random();
        }

        return Response.ok("CPU burn terminato dopo " + milliseconds + " ms").build();
    }

    @GET
    @Path("/burn-multi")
    public Response burnCpuMultiCore(@QueryParam("ms") long milliseconds,
                                     @QueryParam("cores") int cores) {

        if (milliseconds <= 0 || cores <= 0) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                           .entity("Valori 'ms' e 'cores' devono essere > 0")
//                           .build();
        	milliseconds=MILLIS_DEFAULT;
        	cores=CORES_DEFAULT;
        }

        long end = System.currentTimeMillis() + milliseconds;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < cores; i++) {
            Thread t = new Thread(() -> {
                while (System.currentTimeMillis() < end) {
                    double waste = Math.sqrt(Math.random()) * Math.random();
                }
            });
            threads.add(t);
            t.start();
        }

        // Attendi la fine di tutti i thread
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("Errore durante l'esecuzione dei thread")
                               .build();
            }
        }

        return Response.ok("CPU burn su " + cores + " core per " + milliseconds + " ms completato").build();
    }
    
    
    
    @GET
    @Path("/burn-loop-random")
    public Response burnLoopRandom() {
        System.out.println("Ricevuta richiesta loop di burn");

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                int randomCores = 1 + random.nextInt(5); // 1-5 core
                long randomMs = 10000 + random.nextInt(20001); // 10000-30000 ms

                System.out.printf("Ciclo %d: occupo %d core per %d ms%n", i, randomCores, randomMs);
                long end = System.currentTimeMillis() + randomMs;
                List<Thread> threads = new ArrayList<>();

                for (int j = 0; j < randomCores; j++) {
                    Thread t = new Thread(() -> {
                        while (System.currentTimeMillis() < end) {
                            double waste = Math.sqrt(Math.random()) * Math.random();
                        }
                    });
                    threads.add(t);
                    t.start();
                }

                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread interrotto");
                        return;
                    }
                }
            }
            System.out.println("Loop di burn completato");
        }).start();

        return Response.ok("Burn loop avviato in background").build();
    }
}
