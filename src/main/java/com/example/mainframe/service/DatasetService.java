package com.example.mainframe.service;

import com.example.mainframe.entity.DatasetInformation;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DatasetService {

    boolean createDataset(HttpSession session, DatasetInformation datasetInformation);

    boolean writeDataset(HttpSession session, String datasetname, String content);

    String getContent(HttpSession session, String datasetname);

    boolean deleteDataset(HttpSession session, String datasetname);

    List<String> getMemberList(HttpSession session, String datasetName);

    List<Map<String, String>> getDatasetList(HttpSession session, String datasetName);
}
