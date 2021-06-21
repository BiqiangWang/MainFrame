package com.example.mainframe.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class JobInformation {

    private String owner;
    @JsonAlias("jobname")
    private String jobName;
    @JsonAlias("jobid")
    private String jobId;
    private String type;
    private String status;

}
