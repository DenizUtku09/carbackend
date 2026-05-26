package ashina.carrental.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ashina.carrental.entities.concretes.Job;


public interface JobDao extends JpaRepository<Job, Integer>{

    Job findJobByTitle(String title);

    
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
     * @param title the job title
     * @return an Optional containing the job, or an empty Optional if not found
     */
    Optional<Job> findByTitle(String title);

    
}
