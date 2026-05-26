package ashina.carrental.business.abstracts;

import java.util.List;
import java.util.Optional;

import ashina.carrental.entities.concretes.Job;

public interface JobService {

    /**
     * Add a new job.
     *
     * @param job the job to register
     * @return the added job
     */
    Job addNewJob(Job job);

    /**
     * Deletes an job by their ID.
     *
     * @param id the ID of the job to delete
     */
    void deleteById(int id);

     /**
     * Updates an job.
     *
     * @param job the updated job
     * @return the updated job
     */
    Job updateJob(Job job);

     /**
     * Retrieves all jobs.
     *
     * @return a list of all jobs
     */
    List<Job> getAllJobs();

     /**
     * Retrieves an job by their unique identifier (ID).
     *
     * @param id the job ID
     * @return an Optional containing the job, or an empty Optional if not found
     */
    Optional<Job> findById(int id);

     /**
     * Retrieves an job by their title.
     *
     * @param title the job ID
     * @return an Optional containing the job, or an empty Optional if not found
     */
    Optional<Job> findByTitle(String title);


    
}
