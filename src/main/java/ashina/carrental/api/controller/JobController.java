package ashina.carrental.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ashina.carrental.business.abstracts.JobService;
import ashina.carrental.entities.concretes.Job;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    // This class serves as the controller for job-related API endpoints

    // Injects an instance of the JobService interface for handling job operations
    @Autowired
    private JobService jobService;

    @PostMapping("/addNewJob")
    public ResponseEntity<Job> addNewJob(@RequestBody Job job){
        // This method handles POST requests to the "/api/jobs/addNewJob" URL and registers a new jobs 

        // Register the new job by calling the addNewJob() method of the JobService interface
        Job savedJob = jobService.addNewJob(job);

         // Return a ResponseEntity with the saved job and HTTP 200 status
        return ResponseEntity.ok(savedJob);
    }

    @PutMapping("/updateJob")
    public ResponseEntity<Job> updateJob(@RequestBody Job job){

        // This method handles PUT requests to the "/api/jobs/updateJob" URL and update a job 

         // Call the updateJob method of the jobService to update the employee
        Job updatedJob = jobService.updateJob(job);

         // Return the updated job in the response body with a HTTP 200 OK status
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){

        // This method handles DELETE requests to the "/api/jobs/deleteById/{id}" URL to delete an employee by ID

        
        return jobService.findById(id).map(job -> {

             // Find the job by ID
            jobService.deleteById(id);

            // Delete the job by calling the deleteById() method of the JobService interface
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // If no job with the given ID is found, return a ResponseEntity with HTTP 404 (NOT_FOUND) status

    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<List<Job>> getAllJobs(){

        // This method handles GET requests to the "/api/jobs/getAllJobs" URL

        // Retrieve all jobs by calling the getAllJobs() method of the JobService interface
        List<Job> jobs = jobService.getAllJobs();

        // Return a ResponseEntity with the retrieved jobs and HTTP 200 status
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Job> findById(@PathVariable int id){

        // This method defines an API endpoint to search for a job by id

        // Handle GET requests to the "api/jobs/findById/{id}" URL

        // The {id} URL parameter represents the id to search for


        return jobService.findById(id)
            // If a job is found, wrap it in a ResponseEntity with HTTP 200 status
            .map(ResponseEntity::ok)
            // If no job is found, return HTTP 404 status
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<Job> findByTitle(@PathVariable String title){

        // This method defines an API endpoint to search for a job by title

        // Handle GET requests to the "api/jobs/findByTitle/{title}" URL

        // The {title} URL parameter represents the id to search for


        return jobService.findByTitle(title)
            // If a job is found, wrap it in a ResponseEntity with HTTP 200 status
            .map(ResponseEntity::ok)
            // If no job is found, return HTTP 404 status
            .orElse(ResponseEntity.notFound().build());
    }
    
}
