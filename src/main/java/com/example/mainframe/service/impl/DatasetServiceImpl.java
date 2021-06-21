package com.example.mainframe.service.impl;

import com.example.mainframe.entity.DatasetInformation;
import com.example.mainframe.service.DatasetService;
import com.example.mainframe.utils.ZosmfUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DatasetServiceImpl implements DatasetService {

    private static String datasetApiPath = "/zosmf/restfiles/ds";

    //创建顺序和分区数据集
    @Override
    public boolean createDataset(HttpSession session, DatasetInformation datasetInformation){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ZosmfUtil.go(session, datasetApiPath + "/" + datasetInformation.getDatasetname(),
                    HttpMethod.POST,
                    datasetInformation,
                    headers,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean writeDataset(HttpSession session, String datasetName, String content){
        try {
            ZosmfUtil.go(session,
                    datasetApiPath + "/" + datasetName,
                    HttpMethod.PUT,
                    content,
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //删除数据集 Delete a sequential and partitioned data set
    @Override
    public boolean deleteDataset(HttpSession session, String datasetName) {
        try {
            ZosmfUtil.go(session,
                    datasetApiPath + "/" + datasetName,
                    HttpMethod.DELETE,
                    null,
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //获取顺序数据集内容 Retrieve the contents of a z/OS data set or member
    @Override
    public String getContent(HttpSession session, String datasetName) {
        String content = "";
        try {
            content = ZosmfUtil.go(session, datasetApiPath + "/" + datasetName,
                    HttpMethod.GET,
                    null,
                    null,
                    String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return content;
    }

    //获取分区数据集中的成员列表 List the members of a z/OS data set
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getMemberList(HttpSession session, String datasetName) {
        List<String> names = new ArrayList<>();
        try {
            List<Map<String, String>> list = (List<Map<String, String>>) ZosmfUtil.go(session,
                    datasetApiPath + "/" + datasetName + "/member",
                    HttpMethod.GET,
                    null,
                    null,
                    Map.class).get("items");
            for (Map<String, String> m : list) {
                names.add(m.get("member"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return names;
    }

    //查询数据集 List the z/OS data sets on a system
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getDatasetList(HttpSession session, String datasetName) {
        List<Map<String, String>> datasetList = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            // set header to query more info about dataset
            headers.add("X-IBM-Attributes", "base");
            datasetList = (List<Map<String, String>>) ZosmfUtil.go(
                    session,
                    datasetApiPath + "?dslevel=" + datasetName,
                    HttpMethod.GET,
                    null,
                    headers,
                    Map.class).get("items");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return datasetList;
    }
}
