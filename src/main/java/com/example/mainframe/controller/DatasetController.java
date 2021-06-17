package com.example.mainframe.controller;

import com.example.mainframe.entity.DatasetInformation;
import com.example.mainframe.service.DatasetService;
import com.example.mainframe.service.LoginService;
import com.example.mainframe.utils.Result;
import com.example.mainframe.utils.StatusCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class DatasetController {

    @Resource
    DatasetService datasetService;

    @Resource
    LoginService loginService;

    @RequestMapping(value = "/creatdataset",method = RequestMethod.POST)
    public Result createDataset(@RequestBody @ApiParam(name = "DatasetInfo对象",value = "传入JSON数据",required = true) DatasetInformation datasetInfo, HttpSession session) {
        if(loginService.notLogin(session)){
            return new Result(true, StatusCode.ERROR,"not login");
        }
        if (datasetService.createDataset(session, datasetInfo)) {
            return new Result(true, StatusCode.OK,"create successfully");
        }
        return new Result(true, StatusCode.ERROR,"create error");
    }

    //查询数据集内容
    @ApiImplicitParam(paramType = "path", name = "datasetName", value = "数据集名字", required = true, dataType = "String")
    @RequestMapping(value = "/dataset/{datasetName}",method = RequestMethod.GET)
    public Result getContent(@PathVariable String datasetName, HttpSession session) {
        if(loginService.notLogin(session)){
            return new Result(true, StatusCode.ERROR,"not login");
        }
        String content = datasetService.getContent(session , datasetName);
        if(content == null){
            return new Result(true, StatusCode.ERROR,"getcontent error");
        }
        return new Result(true, StatusCode.OK,"getcontent successfully",content);
    }

    //查询数据集成员
    @ApiImplicitParam(paramType = "path", name = "datasetName", value = "数据集名字", required = true, dataType = "String")
    @RequestMapping(value = "/datasetMember/{datasetName}", method = RequestMethod.GET)
    public Result getMemberList(@PathVariable String datasetName, HttpSession session) {
        if(loginService.notLogin(session)){
            return new Result(true, StatusCode.ERROR,"not login");
        }
        List<String> members = datasetService.getMemberList(session , datasetName);
        if(members == null){
            return new Result(true, StatusCode.ERROR,"getMemberList error");
        }
        return new Result(true, StatusCode.OK,"getMemberList successfully",members);
    }

    //查询数据集
    @ApiImplicitParam(paramType = "path", name = "datasetName", value = "数据集名字", required = true, dataType = "String")
    @RequestMapping(value = "/findDataset/{datasetName}", method = RequestMethod.GET)
    public Result getDatasetList(@PathVariable String datasetName, HttpSession session) {
        if(loginService.notLogin(session)){
            return new Result(true, StatusCode.ERROR,"not login");
        }
        List<Map<String,String>> datasetList = datasetService.getDatasetList(session , datasetName);
        if(datasetList == null){
            return new Result(true, StatusCode.ERROR,"getDatasetList error");
        }
        return new Result(true, StatusCode.OK,"getDatasetList successfully",datasetList);
    }


    //删除数据集
    @ApiImplicitParam(paramType = "path", name = "datasetName", value = "数据集名字", required = true, dataType = "String")
    @RequestMapping(value ="/dataset/{datasetName}", method = RequestMethod.DELETE)
    public Result delete(HttpSession session , @PathVariable String datasetName) {
        if(loginService.notLogin(session)){
            return new Result(true, StatusCode.ERROR,"not login");
        }
        if(datasetService.deleteDataset(session , datasetName)){
            return new Result(true,StatusCode.OK,"delete successfully");
        }
        return new Result(true, StatusCode.ERROR,"delete error");
    }
}
