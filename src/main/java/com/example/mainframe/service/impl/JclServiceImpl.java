package com.example.mainframe.service.impl;

import com.example.mainframe.entity.JobInformation;
import com.example.mainframe.entity.JobOutputInforamtion;
import com.example.mainframe.service.JclService;
import com.example.mainframe.utils.ZosmfUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JclServiceImpl implements JclService {

    @Override
    public List<JobOutputInforamtion> submitJcl(HttpSession session,String jcl){
        JobInformation jobInformation = ZosmfUtil.go(session, "/zosmf/restjobs/jobs", HttpMethod.PUT, jcl, null, JobInformation.class);
        if(jobInformation != null && ZosmfUtil.isReady(session,"zosmf/restjobs/jobs/" + jobInformation.getJobName() + "/" + jobInformation.getJobId(),20)){
            //获取输出
            String path = "zosmf/restjobs/jobs/" + jobInformation.getJobName() + "/" + jobInformation.getJobId() + "/file";
            @SuppressWarnings("unchecked")
            List<Map<String,Object>> jobOutput = ZosmfUtil.go(session, path, HttpMethod.GET, null, null, List.class);
            List<JobOutputInforamtion> result = new ArrayList<>();

            for (Map<String, Object> map : jobOutput) {
                // resolve list item
                JobOutputInforamtion item = new JobOutputInforamtion();
                item.setId((int) map.get("id"));
                item.setDdName(map.get("ddname").toString());
                item.setJobId(map.get("jobid").toString());
                item.setJobName(map.get("jobname").toString());
                item.setStepName(map.get("stepname").toString());
                item.setSubSystem(map.get("subsystem").toString());
                String outputPath = path + "/" + item.getId() + "/records";
                // get output of every list item
                try {
                    item.setOutput(ZosmfUtil.go(session, outputPath, HttpMethod.GET, null, null, String.class));
                } catch (Exception e) {
                    item.setOutput("");
                    e.printStackTrace();
                }
                result.add(item);
            }
            return result;

        }
        return null;
    }
}
