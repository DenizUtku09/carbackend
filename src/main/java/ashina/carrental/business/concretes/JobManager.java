package ashina.carrental.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashina.carrental.business.abstracts.JobService;
import ashina.carrental.dataAccess.abstracts.JobDao;
import ashina.carrental.entities.concretes.Job;

@Service
public class JobManager implements JobService{

    @Autowired
    private JobDao jobDao;

    /**
     * Adds a new job by saving the job object using the jobDao.
     * @param job The job to be registered.
     * @return The registered job.
     */
    @Override
    public Job addNewJob(Job job) {
        return jobDao.save(job);
    }

    /**
    * Deletes an job by their ID from the data source using the jobDao.
    * @param id The ID of the job to delete.
    */
    @Override
    public void deleteById(int id) {
        jobDao.deleteById(id);
    }

    /**
    * Updates an job by saving the updated job object using the jobDao.
    * @param job The updated job object.
    * @return The updated job.
    * @throws RuntimeException if the job to be updated is not found.
    */
    @Override
    public Job updateJob(Job job) {

        Optional<Job> existingJob = jobDao.findById(job.getId());

        if (existingJob.isPresent()) {

            Job updatedJob = jobDao.save(job);

            return updatedJob;
        } else {
            throw new RuntimeException("Job not found.");
        }

    }

    /**
     * Retrieves a list of all jobs from the data source using the jobDao.
     * @return A list of all jobs.
     */
    @Override
    public List<Job> getAllJobs() {
        return jobDao.findAll();
    }

    /**
     * Retrieves an job by their ID from the data source using the jobDao.
     * @param id The ID of the job to retrieve.
     * @return An optional containing the job if found, or empty if not found.
     */
    @Override
    public Optional<Job> findById(int id) {
        return jobDao.findById(id);
    }

    /**
     * Retrieves an job by their title from the data source using the jobDao.
     * @param title The title of the job to retrieve.
     * @return An optional containing the job if found, or empty if not found.
     */
    @Override
    public Optional<Job> findByTitle(String title) {
       return jobDao.findByTitle(title);
    }
    
}
