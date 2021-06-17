package com.example.mainframe.entity;

import lombok.Data;

@Data
public class DatasetInformation {
    private String datasetname;
    private String volser;
    private String unit;
    private String dsorg;
    private String alcunit;
    private Integer primary;
    private Integer secondary;
    private Integer dirblk;
    private Integer avgblk;
    private String recfm;
    private Integer blksize;
    private Integer lrecl;
    private String storeclass;
    private String mgntclass;
    private String dataclass;
}
